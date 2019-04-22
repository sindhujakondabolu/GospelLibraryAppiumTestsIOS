var LDS = LDS || {};

/**
 The LDS.annotation module.
 */
LDS.annotation = function () {
    var self = {};

    self.getRectsForAnnotationID = function(annotationID) {
        var range = document.createRange();
        range.selectNodeContents(document.querySelector('[annotationID="' + annotationID + '"]'));
        var clientRects = LDS.selection.getRectsForRange(range);
        var cgRects = new Array();
        for (var i = 0; i < clientRects.length; i++) {
            cgRects[i] = LDS.gospelLibrary.getCGRectFromClientRect(clientRects[i]);
        }
        return cgRects;
    };

    self.getAnnotationIDAtPoint = function(x, y) {
        var element = document.elementFromPoint(x, y);
        return getAnnotationID(element);
    };

    var getAnnotationID = function(element) {
        if (element.getAttribute('annotationID') != null) {
            return element.getAttribute('annotationID');
        }
        return getAnnotationID(element.parentElement);
    };
    
    return self;
}();
