/**
 The LDS Javascript namespace.
 */
var LDS = LDS || {};

/**
 The LDS.gospelLibrary module.
 */
LDS.gospelLibrary = function () {
    var self = {};
    
    self.getTextareaRects = function() {
        var output = [];
        
        var elements = document.getElementsByClassName('textarea');
        for (var i = 0; i < elements.length; i++) {
            var element = elements[i];
            var elementWithAID = self.getNearestSiblingWithAIDAttribute(element);
            var aid = elementWithAID.getAttribute('data-aid');
            if (aid !== null) {
                var rect = element.getBoundingClientRect();
                output.push({
                            aid: aid,
                            rect: self.getCGRectFromClientRect(rect)
                            });
            }
        }
        
        return output;
    };
    
    self.getNearestSiblingWithAIDAttribute = function(element) {
        if (element === null) {
            return null;
        }
        
        if (element.getAttribute('data-aid') === null) {
            var parentElement = element.parentElement;

            var index = -1;
            for (var i = 0; i < parentElement.childElementCount; i++) {
                var childElement = parentElement.children[i];
                if (childElement === element) {
                    index = i;
                    break;
                }
            }
            
            if (index !== -1) {
                for (var i = index; i >= 0; i--) {
                    var childElement = parentElement.children[i];
                    var aid = childElement.getAttribute('data-aid');
                    if (aid !== null) {
                        return childElement;
                    }
                }
            }
        }

        return element;
    };
    
    self.isRefAtPoint = function(x, y) {
        var offsetY = y - window.scrollY;
        var element = document.elementFromPoint(x, offsetY);
        return self.getFootnoteElementForTouchPoint(element, x, offsetY) != null;
    };
    
    self.getRectsForHighlights = function(json) {
        var output = {};
        for (var highlightID in json) {
            if (highlightID) {
                var wordOffset = json[highlightID];
                var rects = self.getRectsForWordOffsets([wordOffset]);
                if (rects === null) {
                    continue;
                }
                output[highlightID] = rects;
            }
        }
        return output;
    };
    
    self.rectForText = function(text) {
        var allFilter = function(node) {
            return NodeFilter.FILTER_ACCEPT;
        };
        var treeWalker = document.createTreeWalker(document.body, NodeFilter.SHOW_TEXT, allFilter, false);
        while (treeWalker.nextNode()) {
            var element = treeWalker.currentNode;
            if (element.textContent.trim() == text) {
                var range = document.createRange();
                range.selectNode(element);
                var rect = range.getBoundingClientRect();
                return self.getCGRectFromClientRect(rect);
            }
        }
        return null;
    };
    
    self.selectionRect = function() {
        var selection = window.getSelection();
        if (selection.rangeCount) {
            var range = selection.getRangeAt(0);
            var rect = range.getBoundingClientRect();
            if (rect.width > 0 && rect.height > 0) {
                return self.getCGRectFromClientRect(rect);
            }
        }
        return null;
    };
    
    self.clearSearchMatches = function() {
        var elements = document.getElementsByClassName('searchMatch');
        for (var j = 0; j < elements.length; j++) {
            elements[j].style.background = '';
        }
    };
    
    self.getRectsForSearchMatchAtIndex = function(index, matchColor, selectedColor) {
        var elements = document.getElementsByClassName('searchMatch');
        if (index < elements.length) {
            var cgRects = [];
            
            for (var j = 0; j < elements.length; j++) {
                var element = elements[j];
                var matchIndex = element.getAttribute('data-match-id');
                
                if (index == parseInt(matchIndex)) {
                    var rects = element.getClientRects();
                    
                    for (var i = 0; i < rects.length; i++) {
                        cgRects.push(self.getCGRectFromClientRect(rects[i]));
                    }
                    
                    element.style.background = selectedColor;
                } else {
                    element.style.background = matchColor;
                }
            }
            return cgRects;
        }
        
        return null;
    };
    
    self.getRefSpanElement = function(element) {
        if (element instanceof HTMLSpanElement && element.getAttribute('data-ref') !== null) {
            return element;
        }
        
        if (element.parentElement instanceof HTMLSpanElement) {
            return self.getRefSpanElement(element.parentElement);
        }
        
        return null;
    };
    
    self.getFootnoteElementForTouchPoint = function(element, touchX, touchY) {
        var minimumTapHeight = 44;
        var paddingX = 10;
        
        var refElement = self.getRefSpanElement(element);
        if (refElement !== null) {
            var rects = refElement.getClientRects();
            
            for (var i = 0; i < rects.length; i++) {
                var rect = rects[i];
                var paddingY = Math.max(0, minimumTapHeight - rect.height) / 2.0;
                
                var leftX = (rect.left - paddingX);
                var rightX = (rect.right + paddingX);
                var topY = (rect.top - paddingY);
                var bottomY = (rect.bottom + paddingY);
                
                // Pad the rect that represents the bounding rect of the current element
                if ((leftX <= touchX && touchX <= rightX) && (topY <= touchY && touchY <= bottomY)) {
                    return refElement;
                }
            }
        }
        
        for (var i = 0; i < element.children.length; i++) {
            var footnoteElement = self.getFootnoteElementForTouchPoint(element.children[i], touchX, touchY);
            if (footnoteElement !== null) {
                return footnoteElement;
            }
        }
        
        return null;
    };
    
    self.getParagraphAIDForRefID = function(refID) {
        var elements = document.querySelectorAll('[data-ref=' + refID + ']');
        for (var i = 0; i < elements.length; i++) {
            var paraElement = LDS.selection.getNearestAncestorWithAIDAttribute(elements[i]);
            if (paraElement !== null) {
                return paraElement.getAttribute("data-aid");
            }
        }
        return null;
    };
    
    self.shouldAllowToggleFullscreen = function(x, y, showFootnotes) {
        var offsetY = y - window.scrollY;
        var element = document.elementFromPoint(x, offsetY);
        if (element instanceof HTMLAnchorElement) {
            return false;
        }  else if (element instanceof HTMLSpanElement) {
            return (showFootnotes === false || self.getRefSpanElement(element) === null);
        } else if (element instanceof HTMLImageElement) {
            return false;
        } else if (element instanceof HTMLVideoElement) {
            return false;
        } else if (element.getAttribute("class") === "image") {
            return false;
        }
        
        var footnoteElement = self.getFootnoteElementForTouchPoint(element, x, y);
        return (showFootnotes === false || footnoteElement === null);
    };
    
    self.getHTMLFromArrayOfHighlights = function(offsets) {
        var range = LDS.selection.getRangeForWordOffsets(offsets);
        if (range === null) {
            return "";
        }
        var container = document.createElement("div");
        container.appendChild(range.cloneContents());
        return container.innerHTML;
    };
    
    self.getSelectionHTML = function() {
        var html = "";
        if (typeof self.getWordOffsetsForSelection !== "undefined") {
            var sel = document.getSelection();
            if (sel.rangeCount > 0) {
                var container = document.createElement("div");
                for (var i = 0, len = sel.rangeCount; i < len; ++i) {
                    container.appendChild(sel.getRangeAt(i).cloneContents());
                }
                html = container.innerHTML;
            }
        } else if (typeof document.selection !== "undefined") {
            if (document.selection.type === "Text") {
                html = document.selection.createRange().htmlText;
            }
        }
        return html;
    };
    
    self.getRectsForWordOffsets = function(wordOffsets) {
        var range = LDS.selection.getRangeForWordOffsets(wordOffsets);
        if (range === null) {
            return null;
        }
        var rects = LDS.selection.getRectsForRange(range);
        
        var cgRects = [];
        for (var i = 0; i < rects.length; i++) {
            cgRects[i] = self.getCGRectFromClientRect(rects[i]);
        }
        
        return cgRects;
    };

    self.getAIDsForSelection = function() {
        var aids = [];

        var range = getSelectionRange();
        if (range === null) {
            return null;
        }
        
        var elements = LDS.selection.getElementsInRangeWithAID(range);

        for (var i = 0; i < elements.length; i++) {
            var element = elements[i];

            var aid = element.getAttribute('data-aid').toString();
            aids.push(aid);
        }

        return aids;
    };

    self.getSelectionWordOffsetsAndRects = function() {
        var output = [];
        
        var wordOffsets = self.getWordOffsetsForSelection();
        if (wordOffsets) {
            for (var i = 0; i < wordOffsets.length; i++) {
                var currentWordOffset = [wordOffsets[i]];
                var range = LDS.selection.getRangeForWordOffsets(currentWordOffset);
                if (range === null) {
                    continue;
                }
                var rects = LDS.selection.getRectsForRange(range);
                
                var cgRects = [];
                for (var j = 0; j < rects.length; j++) {
                    cgRects[j] = self.getCGRectFromClientRect(rects[j]);
                }
                
                output[i] = {data: currentWordOffset, rects: cgRects};
            }
        } else {
            //  No valid AIDs in selection block
            var range = getSelectionRange();
            if (range === null) {
                return null;
            }
            
            var rects = LDS.selection.getRectsForRange(range);
            
            var cgRects = [];
            
            for (var j = 0; j < rects.length; j++) {
                cgRects[j] = self.getCGRectFromClientRect(rects[j]);
            }
            
            output.push({rects: cgRects});
        }
        
        return output;
    };
    
    var filter = function(array, predicate) {
        var results = [];
        for (var i = 0, n = array.length; i < n; ++i) {
            if (predicate(array[i])) {
                results.push(array[i]);
            }
        }
        return results;
    };
    
    self.getFullyVisibleElementIDs = function() {
        var elements = document.querySelectorAll('[id]');
        
        var fullyVisibleElements = filter(elements, function(element) {
            var rect = element.getBoundingClientRect();
            return (rect.top >= 0 && rect.top + rect.height <= window.innerHeight && rect.height > 0 && rect.width > 0);
        });
        
        return fullyVisibleElements.map(function(element) {
            return element.id;
        });
    };
    
    self.getSubitemURI = function() {
        return document.documentElement.getAttribute('data-uri');
    };
    
    self.getSubitemDocID = function() {
        return document.documentElement.getAttribute('data-aid');
    };
    
    self.getRectsForParagraphID = function(id) {
        var element = document.querySelector("[id='" + id + "']");
        var rects = element.getClientRects();
        
        var cgRects = [];
        for (var i = 0; i < rects.length; i++) {
            cgRects.push(self.getCGRectFromClientRect(rects[i]));
        }
        return cgRects;
    };
    
    self.getRectsForElementsWithAIDs = function(aids) {
        var cgRects = [];
        for (var i = 0; i < aids.length; i++) {
            cgRects[i] = getCGRectForElementWithAID(aids[i]);
        }
        return cgRects;
    };
    
    self.getElementAndRectWithAIDFromPoint = function(x, y) {
        var elements = LDS.selection.getAIDElements();
        for (var i = 0; i < elements.length; i++) {
            var boundingRect = elements[i].getBoundingClientRect();
            var offsetY = y - window.scrollY;
            if (offsetY >= boundingRect.top && offsetY <= (boundingRect.top + boundingRect.height)) {
                return { 'aid': elements[i].getAttribute('data-aid'), 'rect': self.getCGRectFromClientRect(boundingRect) };
            }
        }
        return null;
    };
    
    self.getCGRectFromClientRect = function(clientRect) {
        return ("{{" + clientRect.left + "," + (clientRect.top + window.scrollY) + "},{" + clientRect.width + "," + clientRect.height + "}}");
    };
    
    self.getWordOffsetsForSelection = function() {
        var range = getSelectionRange();
        if (range === null) {
            return null;
        }
        return LDS.selection.getWordOffsetsFromRange(range);
    };
    
    var getSelectionRange = function() {
        var selection = document.getSelection();
        if (selection.rangeCount > 0) {
            return selection.getRangeAt(0);
        }
        return null;
    };
    
    /**
     The bounding rect for the element with the given `aid`.
     */
    var getCGRectForElementWithAID = function(aid) {
        var element = LDS.selection.getElementByAID(aid);
        if (element) {
            var rect = element.getBoundingClientRect();
            return self.getCGRectFromClientRect(rect);
        }
        return {};
    };
    
    self.getRefIDForTopVisibleRefElement = function() {
        var elements = document.querySelectorAll('[data-ref]');
        for (var i = 0; i < elements.length; i++) {
            var rect = elements[i].getBoundingClientRect();
            if (rect.top >= 0 && rect.height > 0 && rect.width > 0) {
                return elements[i].getAttribute('data-ref');
            }
        }
        return null;
    };
    
    self.getTopVisibleParagraphID = function(topmargin) {
        var elements = LDS.selection.getAIDElements();
        var rect;
        var element = null;
        
        for (var i = 0; i < elements.length; i++) {
            rect = elements[i].getBoundingClientRect();
            if (rect.top >= topmargin && rect.height > 0 && rect.width > 0) {
                return elements[i].getAttribute('id');
            }
        }
        
        return null;
    };
    
    /**
     Get the AID and rectangle of the top element within the view
     */
    self.getTopVisibleAID = function(topmargin) {
        var elements = LDS.selection.getAIDElements();
        var rect;
        
        var element = null;
        
        for (var i = 0; i < elements.length; i++) {
            rect = elements[i].getBoundingClientRect();
            if (rect.top >= topmargin && rect.height > 0 && rect.width > 0) {
                element = elements[i];
                break;
            }
        }
        
        if (element === null) {
            return {};
        }
        
        return {
            'aid': element.getAttribute('data-aid'),
            'rect': self.getCGRectFromClientRect(rect)
        };
    };
    
    self.decorateVideos = function(position) {
        var videos = document.getElementsByTagName('video');
        for (var i = 0, n = videos.length; i < n; i++) {
            self.decorateVideo(i);
        }
    };
    
    self.decorateVideo = function(position) {
        var videos = document.getElementsByTagName('video');
        var video = videos[position];
        
        var createEventHandler = function(event, position) {
            return function() {
                var json = {
                    'action': 'video',
                    'event': event,
                    'index': position,
                    'duration': video.duration,
                    'currentTime': video.currentTime
                };
                window.webkit.messageHandlers.handler.postMessage(json);
            };
        };
        var events = ['play', 'pause', 'ended'];
        for (var eventIndex = 0, numEvents = events.length; eventIndex < numEvents; eventIndex++) {
            var event = events[eventIndex];
            video.addEventListener(event, createEventHandler(event, position), false);
        }
    };
    
    self.getVideosJSON = function() {
        var videosData = [];
        
        var videos = document.getElementsByTagName('video');
        for (var i = 0, n = videos.length; i < n; i++) {
            var video = videos[i];
            
            videosData.push({'frame': self.getCGRectFromClientRect(video.getBoundingClientRect())});
        }
        
        return videosData;
    };
    
    self.getVideoSourcesJSON = function(position) {
        var videoSourcesData = [];
        
        var videos = document.getElementsByTagName('video');
        if (position < videos.length) {
            var video = videos[position];
            var videoID = video.attributes["data-video-id"].value;
            
            var sources = video.getElementsByTagName('source');
            for (var i = 0, n = sources.length; i < n; i++) {
                var source = sources[i];
                
                videoSourcesData.push({
                    'URL': source.src,
                    'type': source.type,
                    'fileSize': source.dataset['file-size'],
                    'width': source.dataset.width,
                    'height': source.dataset.height,
                    'videoID': videoID
                });
            }
        }
        
        return videoSourcesData;
    };
    
    self.insertVideoLocalSource = function(position, fileURL) {
        var videos = document.getElementsByTagName('video');
        if (position < videos.length) {
            var video = videos[position];
            
            var isPlaying = !video.paused;
            if (isPlaying) {
                video.pause();
            }
            
            var localVideo = document.createElement('video');
            var videoAttributes = video.attributes;
            for (var i = videoAttributes.length - 1; i >= 0; i--) {
                localVideo.setAttribute(videoAttributes[i].name, videoAttributes[i].value);
            }
            
            var localSource = document.createElement('source');
            localSource.src = fileURL;
            localSource.type = 'video/mp4';
            localVideo.appendChild(localSource);

            // Copy over all of the original sources
            var sources = video.getElementsByTagName('source');
            for (var i = 0, n = sources.length; i < n; i++) {
                var source = sources[i];

                // If the video source already contains the localVideoSource no need to re-insert it.
                if (source.src === fileURL) {
                    continue;
                }
                localVideo.appendChild(source.cloneNode(true));
            }

            video.parentNode.replaceChild(localVideo, video);
            
            if (isPlaying) {
                localVideo.play();
            }
            
            self.decorateVideo(position);
        }
    };
    
    self.removeVideoLocalSource = function(position) {
        var videos = document.getElementsByTagName('video');
        if (position < videos.length) {
            var video = videos[position];

            var isPlaying = !video.paused;
            if (isPlaying) {
                video.pause();
            }
            
            var remoteVideo = document.createElement('video');
            var videoAttributes = video.attributes;
            for (var i = videoAttributes.length - 1; i >= 0; i--) {
                remoteVideo.setAttribute(videoAttributes[i].name, videoAttributes[i].value);
            }

            // Copy over all the sources except for those that are local (prefixed by 'file')
            var sources = video.getElementsByTagName('source');
            for (var i = 0, n = sources.length; i < n; i++) {
                var source = sources[i];
                if (source.src.indexOf('file') !== 0) {
                    remoteVideo.appendChild(source.cloneNode(true));
                }
            }

            video.parentNode.replaceChild(remoteVideo, video);
            
            if (isPlaying) {
                remoteVideo.play();
            }
            
            self.decorateVideo(position);
        }
    };
    
    self.pauseAllVideos = function () {
        var videos = document.getElementsByTagName('video');
        for (var i = 0; i < videos.length; i++) {
            videos[i].pause();
        }
    };
    
    self.pauseVideo = function() {
        var video = document.querySelector('video');
        if (video !== null) {
            video.pause();
        }
    };
    
    /**
     Returns the multi-src image's renditions as JSON.
     
     This data can be used to present a zooming image view outside of the web view.
     */
    self.getMultiSrcImageJSON = function(imgID) {
        var renditionsData = [];
        var data = {
            "renditions": renditionsData
        };
        
        var image = document.querySelector("img[id='" + imgID + "']");
        if (image) {
            if (image.alt) {
                data.title = image.alt;
            } else if (image.title) {
                data.title = image.title;
            }
            for (var i = 0, n = image.attributes.length; i < n; i++) {
                var attribute = image.attributes[i];
                var result = attribute.name.match(/^data-src-(\d+)x(\d+)$/);
                if (result) {
                    var width = Number(result[1]);
                    var height = Number(result[2]);
                    renditionsData.push({
                        'url': attribute.value,
                        'size': '{' + width + ',' + height + '}',
                    });
                }
            }
        }
        
        return data;
    };
    
    return self;
}();
