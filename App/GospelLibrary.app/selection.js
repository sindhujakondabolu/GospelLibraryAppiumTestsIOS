/**
 The LDS Javascript namespace.
 */
var LDS = LDS || {};

/**
 The LDS.selection module.
 */
LDS.selection = function () {
    var self = {};
    
    self.getWordOffsetsFromRange = function (range) {
        var wordOffsets = [];
        
        var startContainer = getFirstContainerInRange(range.startContainer, range);
        var startCharacterOffset = 0;
        if (startContainer === range.startContainer) {
            startCharacterOffset = range.startOffset;
        }
        range.setStart(startContainer, startCharacterOffset);
        
        var elements = self.getElementsInRangeWithAID(range);
        var startElement = self.getNearestAncestorWithAIDAttribute(startContainer);
        
        var isFirstWord = false;
        var isLastWord = false;
        
        if (startElement === null) {
            // Get first element in range with AID
            if (elements.length === 0) {
                return null;
            }
            
            startElement = elements[0]; // First element
            isFirstWord = true;
        }
        
        var endContainer = range.endContainer;
        var endCharacterOffset = range.endOffset;
        var endElement = self.getNearestAncestorWithAIDAttribute(endContainer);
        
        if (endElement === null) {
            if (elements.length === 0) {
                return null;
            }
            endElement = elements[elements.length - 1]; // Last element
            isLastWord = true;
        }
        
        for (var i = 0; i < elements.length; i++) {
            var element = elements[i];
            
            var aid = element.getAttribute('data-aid').toString();
            var startOffset = -1;
            var endOffset = -1;
            
            if (element === startElement) {
                if (isFirstWord) {
                    startOffset = -1;
                } else {
                    startOffset = getWordOffset(startContainer, startCharacterOffset, startElement, true);
                }
                
                if (startElement.getAttribute('data-aid') !== endElement.getAttribute('data-aid')) {
                    endOffset = -1;
                } else {
                    endOffset = getWordOffset(endContainer, endCharacterOffset, endElement, false);
                }
                
            } else if (element === endElement) {
                
                if (startElement.getAttribute('data-aid') !== endElement.getAttribute('data-aid')) {
                    startOffset = -1;
                } else {
                    startOffset = getWordOffset(startContainer, startCharacterOffset, startElement, true);
                }
                
                if (isLastWord) {
                    endOffset = -1;
                } else {
                    endOffset = getWordOffset(endContainer, endCharacterOffset, endElement, false);
                }
            }
            
            var annotation = {aid: aid, startOffset: startOffset, endOffset: endOffset};
            wordOffsets.push(annotation);
        }
        
        return wordOffsets;
    };
    
    var getFirstContainerInRange = function (textNode, range) {
        var allFilter = function (node) {
            if (rangeContainsNode(range, node)) {
                return NodeFilter.FILTER_ACCEPT;
            }
            return NodeFilter.FILTER_SKIP;
        };
        var treeWalker = document.createTreeWalker(document.body, NodeFilter.SHOW_TEXT, allFilter, false);
        while (treeWalker.nextNode()) {
            return treeWalker.currentNode;
        }
        return null;
    };
    
    var addToArray = function (element, array) {
        if (array.indexOf(element) === -1) {
            array.push(element);
        }
        return array;
    };
    
    self.getElementsInRangeWithAID = function (range) {
        var elements = [];
        
        var commonAncestor = range.commonAncestorContainer;
        var nodesInRangeFilter = function (node) {
            if (rangeContainsNode(range, node)) {
                return NodeFilter.FILTER_ACCEPT;
            }
            return NodeFilter.FILTER_SKIP;
        };
        var treeWalker = document.createTreeWalker(commonAncestor, NodeFilter.SHOW_ELEMENT, nodesInRangeFilter, false);
        while (treeWalker.nextNode()) {
            var element = treeWalker.currentNode;
            if (hasAID(element)) {
                elements = addToArray(element, elements);
            }
        }
        
        if (elements.length === 0) {
            if (hasAID(commonAncestor)) {
                elements = addToArray(commonAncestor, elements);
            } else {
                var ancestor = self.getNearestAncestorWithAIDAttribute(commonAncestor);
                if (ancestor !== null && hasAID(ancestor)) {
                    elements = addToArray(ancestor, elements);
                }
            }
        }
        
        return elements;
    };
    
    self.getRectsForRange = function (range) {
        var commonAncestor = range.commonAncestorContainer;
        
        var nodesInRangeFilter = function (node) {
            if (rangeContainsNode(range, node)) {
                return NodeFilter.FILTER_ACCEPT;
            }
            return NodeFilter.FILTER_SKIP;
        };
        
        var treeWalker = document.createTreeWalker(commonAncestor, NodeFilter.SHOW_TEXT, nodesInRangeFilter, false);
        
        var rects = [];
        
        while (treeWalker.nextNode()) {
            var clientRects = getRectsForRangeInNode(treeWalker.currentNode, range);
            for (var i = 0; i < clientRects.length; i++) {
                rects.push(clientRects[i]);
            }
        }
        
        if (rects.length === 0) {
            var clientRects = getRectsForRangeInNode(commonAncestor, range);
            for (var i = 0; i < clientRects.length; i++) {
                rects.push(clientRects[i]);
            }
        }
        
        return rects;
    };
    
    self.getAIDElements = function () {
        var elements = document.querySelectorAll('[data-aid]:not(html)');
        return elements;
    };
    
    self.getAllAIDs = function() {
        var aids = Array();
        var elements = self.getAIDElements();
        for (var i = 0; i < elements.length; i++) {
            aids[i] = elements[i].getAttribute('data-aid');
        }
        return aids;
    };
    
    self.getRangeForWordOffsets = function (wordOffsets) {
        if (wordOffsets.length === 0) {
            return null;
        }
        
        var start = null;
        var end = null;
        
        var elements = self.getAIDElements();
        for (var i = 0; i < elements.length; i++) {
            var aid = elements[i].getAttribute('data-aid');
            for (var j = 0; j < wordOffsets.length; j++) {
                var offset = wordOffsets[j];
                if (start === null && offset.aid === aid) {
                    start = offset;
                    end = offset;
                } else if (offset.aid === aid) {
                    end = offset;
                }
            }
        }
        
        if (start == null || end == null) {
            return null;
        }
        
        var startElement = self.getElementByAID(start.aid);
        var startContainerAndOffset = getContainerAndOffset(startElement, start.startOffset, true);
        
        var endElement = self.getElementByAID(end.aid);
        var endContainerAndOffset = getContainerAndOffset(endElement, end.endOffset, false);
        
        var range = document.createRange();
        range.setStart(startContainerAndOffset.container, startContainerAndOffset.offset);
        range.setEnd(endContainerAndOffset.container, endContainerAndOffset.offset);
        return range;
    };
    
    self.getNearestAncestorWithAIDAttribute = function (element) {
        if (element === null) {
            return null;
        }
        if (!hasAID(element)) {
            return self.getNearestAncestorWithAIDAttribute(element.parentElement);
        }
        
        return element;
    };
    
    self.getElementByAID = function (aid) {
        return document.querySelector('[data-aid="' + aid + '"]');
    };
    
    var getWordOffset = function (container, offset, element, isStart) {
        var foundWordOffset = false;
        var totalWords = 0;
        var wordOffset = 0;
        var currentOffset = 0;
        
        var allFilter = function (node) {
            return NodeFilter.FILTER_ACCEPT;
        };
        
        var treeWalker = document.createTreeWalker(element, NodeFilter.SHOW_TEXT, allFilter, false);
        while (treeWalker.nextNode()) {
            var node = treeWalker.currentNode;
            currentOffset = 0; // Reset, new node
            
            var words = node.nodeValue.split(/\s+|[\u002D\u058A\u05BE\u1400\u1806\u2010-\u2015\u2053\u207B\u208B\u2212\u2E17\u2E1A\u2E3A-\u301C\u3030\u30A0\uFE31\uFE32\uFE58\uFE63\uFF0D]/g);
            for (var i = 0; i < words.length; i++) {
                var wordLength = words[i].length;
                var word = words[i].trim();
                
                if (word.length === 0) {
                    // Empty string
                    currentOffset += wordLength;
                    continue;
                }
                
                totalWords++; // Add to our total count of words
                
                if (foundWordOffset) {
                    continue; // Already found the word, so just keep looping until we get the totalWords count
                }
                
                // We haven't found the word yet, so increment our word offset
                wordOffset++;
                
                if (node !== container) {
                    // Not in the container, so just continue
                    continue;
                }
                
                var plusSpace = (words.length > 1);
                var nextOffset = (currentOffset + wordLength + plusSpace);
                
                if ((isStart && offset < nextOffset) || (!isStart && offset <= nextOffset)) {
                    foundWordOffset = true;
                }
                currentOffset += (wordLength + plusSpace);
                
                if (isStart && (currentOffset === offset) && (container === node)) {
                    /*
                     If the selection start falls on the end of a tag, Javascript uses the last character offset in the current node.
                     Because we use word offsets instead of character offsets, we need to use the word offset of the following word in the next node
                     */
                    
                    wordOffset++;
                    foundWordOffset = true;
                }
            }
        }
        
        if ((isStart && wordOffset === 1) || (!isStart && wordOffset === totalWords)) {
            wordOffset = -1; // Start or end of paragraph
        }
        
        return wordOffset;
    };
    
    var getContainerAndOffset = function (element, wordOffset, isStart) {
        var currentWordOffset = 0;
        
        var allFilter = function (node) {
            return NodeFilter.FILTER_ACCEPT;
        };
        
        var maxOffset = getMaxWordOffset(element);
        if (wordOffset > maxOffset) {
            wordOffset = maxOffset;
        }
        if (isStart && (wordOffset === -1 || wordOffset === 1)) {
            // Find the first text node and return character 0
            var container = getFirstTextNode(element);
            return {container: container, offset: 0};
        } else if (!isStart && wordOffset === -1) {
            wordOffset = maxOffset;
        }
        
        var treeWalker = document.createTreeWalker(element, NodeFilter.SHOW_TEXT, allFilter, false);
        while (treeWalker.nextNode()) {
            var node = treeWalker.currentNode;
            var words = node.nodeValue.split(/\s+|[\u002D\u058A\u05BE\u1400\u1806\u2010-\u2015\u2053\u207B\u208B\u2212\u2E17\u2E1A\u2E3A-\u301C\u3030\u30A0\uFE31\uFE32\uFE58\uFE63\uFF0D]/g);
            var offset = 0;
            var length = words.length;
            
            for (var i = 0; i < length; i++) {
                var word = words[i];
                
                var charOffsetOfWord = word.length;
                
                if (length > 1 && i !== (length - 1)) {
                    charOffsetOfWord++; // Add +1 for the space if more than 1 word in node, and its not the last word
                }
                
                offset += charOffsetOfWord;
                
                word = word.trim();
                if (word.length === 0) {
                    continue; // No word
                }
                
                currentWordOffset++;
                
                if (wordOffset === currentWordOffset) {
                    if (isStart) {
                        // Offset should be at the start of this word, not the end.
                        offset -= charOffsetOfWord;
                    } else if (length > 1 && i !== (length - 1)) {
                        offset--;
                    }
                    return {container: node, offset: offset};
                }
            }
        }
        
        return null;
    };
    
    var hasAID = function (element) {
        return element.nodeType !== Node.TEXT_NODE && element.attributes !== null && element.attributes.getNamedItem('data-aid') !== null;
    };
    
    var getFirstTextNode = function (node) {
        var children = node.childNodes;
        for (var i = 0; i < children.length; i++) {
            var child = children[i];
            if (child.nodeType === Node.TEXT_NODE) {
                return child;
            }
            
            var textNode = getFirstTextNode(child);
            if (textNode !== null) {
                return textNode;
            }
        }
        return null;
    };
    
    var getMaxWordOffset = function (element) {
        var wordOffset = 0;
        var allFilter = function (node) {
            return NodeFilter.FILTER_ACCEPT;
        };
        var treeWalker = document.createTreeWalker(element, NodeFilter.SHOW_TEXT, allFilter, false);
        while (treeWalker.nextNode()) {
            var words = treeWalker.currentNode.nodeValue.split(/\s+|[\u002D\u058A\u05BE\u1400\u1806\u2010-\u2015\u2053\u207B\u208B\u2212\u2E17\u2E1A\u2E3A-\u301C\u3030\u30A0\uFE31\uFE32\uFE58\uFE63\uFF0D]/g);
            for (var i = 0; i < words.length; i++) {
                var word = words[i].trim();
                if (word.length === 0) {
                    continue; // No word
                }
                wordOffset++;
            }
        }
        return wordOffset;
    };
    
    /**
     Whether the `range` contains the `node`.
     */
    var rangeContainsNode = function (range, node) {
        var nodeRange = document.createRange();
        nodeRange.selectNodeContents(node);
        return (range.compareBoundaryPoints(Range.END_TO_START, nodeRange) === -1 &&
                range.compareBoundaryPoints(Range.START_TO_END, nodeRange) === 1);
    };
    
    var getRectsForRangeInNode = function (node, range) {
        var tempRange = document.createRange();
        tempRange.selectNodeContents(node);
        
        if (range.startContainer === node) {
            tempRange.setStart(node, range.startOffset);
        }
        if (range.endContainer === node) {
            tempRange.setEnd(node, range.endOffset);
        }
        
        return tempRange.getClientRects();
    };
    
    return self;
}();
