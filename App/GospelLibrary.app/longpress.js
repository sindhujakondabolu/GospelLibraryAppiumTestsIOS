LongPressDetector = function (body, handler) {
    this.handler = handler;
    this.body = body;
    
    body.addEventListener('touchstart', this, false);
};

LongPressDetector.prototype.handleEvent = function (event) {
    switch (event.type) {
        case 'touchstart':
            this.onTouchStart(event);
            break;
        case 'touchmove':
            this.onTouchMove(event);
            break;
        case 'touchend':
            this.onTouchEnd(event);
            break;
    }
};

LongPressDetector.prototype.onTouchStart = function (event) {
    var element = event.changedTouches[0].target;
    
    this.body.addEventListener('touchend', this, false);
    this.body.addEventListener('touchmove', this, false);
    
    this.startX = this.currentX = event.touches[0].clientX;
    this.startY = this.currentY = event.touches[0].clientY;
    this.followTappedLink = true;
    
    var that = this;
    
    if (element instanceof HTMLAnchorElement) {
        // process the href link into something we can use
        var href = element.getAttribute("href");
        this.targetElement = element;
        
        // if this href begins with http, skip the long press and pass the http link through unchanged
        if (href.indexOf("http") !== 0) {
            // prevent the webview browser from processing links
            function handler(event) {
                event.stopPropagation();
                return false;
            }
            
            this.body.onclick = handler;
            
            // create the long=press timer
            this.longTapTimer = setTimeout(function () {
                var res = href.split("?");
                var uri = res[0];
                var originalParams = "";
                
                if (res.length > 1) {
                    originalParams = '?' + res[1];
                }
                
                var json = {
                    'action': 'longpress',
                    'point': '{' + event.touches[0].clientX + ',' + event.touches[0].clientY + '}',
                    'url': uri + originalParams,
                    'text': (element.text).replace(/&/g, "%26")
                };
                window.webkit.messageHandlers.handler.postMessage(json);
                                           
                that.followTappedLink = false;
                that.ref = null;
                that.targetElement = null;
            }, 500);
        }
    } else if (element instanceof HTMLSpanElement) {
        var ref = LDS.gospelLibrary.getRefSpanElement(element);
        if (ref !== null) {
            this.ref = ref;
            this.targetElement = element;
        }
        this.longTapTimer = setTimeout(function () {
            // On long press, don't handle ref, let WKWebView handle the event
            that.ref = null;
        }, 500);
    } else {
        var footnoteElement = LDS.gospelLibrary.getFootnoteElementForTouchPoint(element, this.startX, this.startY);
        if (footnoteElement !== null) {
            var ref = footnoteElement.getAttribute("data-ref");
            if (ref !== null) {
                this.ref = ref;
                this.targetElement = footnoteElement;
            }
            this.longTapTimer = setTimeout(function () {
                // On long press, don't handle ref, let WKWebView handle the event
                that.ref = null;
            }, 500);
        }
    }
};

LongPressDetector.prototype.onTouchMove = function (event) {
    this.currentX = event.touches[0].clientX;
    this.currentY = event.touches[0].clientY;
    
    var element = event.changedTouches[0].target;
    if (element instanceof HTMLSpanElement && this.ref !== null) {
        if (Math.abs(this.currentX - this.startX) >= 4 || Math.abs(this.currentY - this.startY) >= 4) {
            if (this.longTapTimer !== null) {
                clearTimeout(this.longTapTimer);
                this.longTapTimer = null;
            }
            this.ref = null;
            this.targetElement = null;
        }
    }
};

LongPressDetector.prototype.onTouchEnd = function (event) {
    if (this.longTapTimer !== null) {
        clearTimeout(this.longTapTimer);
        this.longTapTimer = null;
    }
    
    if (Math.abs(this.currentX - this.startX) >= 4 || Math.abs(this.currentY - this.startY) >= 4) {
        this.ref = null;
        this.targetElement = null;
    }
    
    if (this.targetElement !== null) {
        if (this.targetElement instanceof HTMLSpanElement && this.ref !== null) {
            // process rca references
            var ref = null;
            if (typeof this.ref !== "string" && this.ref.hasAttribute("data-ref")) {
                ref = this.ref.getAttribute("data-ref");
            }
            if (ref === null) {
                ref = this.ref;
            }
            
            var rects = this.targetElement.getClientRects();
            var cgRects = [];
            for (var i = 0; i < rects.length; i++) {
                cgRects[i] = LDS.gospelLibrary.getCGRectFromClientRect(rects[i]);
            }
            
            var touchPoint = '{' + this.currentX + ',' + (this.currentY + window.scrollY) + '}';
            
            var refInfo = {
                'action': 'refTapped',
                'ref': ref,
                'rects': cgRects,
                'touch': touchPoint
            };
            if (this.targetElement.childNodes.length === 2) {
                refInfo.name = this.targetElement.childNodes[0].textContent;
                refInfo.text = this.targetElement.childNodes[1].textContent;
            } else if (this.targetElement.childNodes.length > 0) {
                refInfo.name = this.targetElement.childNodes[0].textContent;
            }
            window.webkit.messageHandlers.handler.postMessage(refInfo);
            
            // reset
            this.ref = null;
            this.targetElement = null;
        } else if (this.followTappedLink && this.targetElement instanceof HTMLAnchorElement) {
            // manually process tappable links instead of letting the webview handle them
            var href = this.targetElement.getAttribute("href");
            event.preventDefault();
            
            var res = href.split("?");
            var uri = res[0];
            var originalParams = "";
            if (res.length > 1) {
                originalParams = '?' + res[1];
            }
            
            var name = encodeURIComponent(this.targetElement.childNodes[0].textContent);
            if (href.indexOf("gl\://") !== 0) {
                var json = {
                    'action': 'linktap',
                    'name': this.targetElement.childNodes[0].textContent,
                    'url': uri + originalParams
                };
                window.webkit.messageHandlers.handler.postMessage(json);
            }
            
            this.ref = null;
            this.targetElement = null;
        } else {
            this.handler(event);
        }
    } else {
        this.handler(event);
    }
};

window.addEventListener("load", function () {
    new LongPressDetector(document.body, function (event) { });
}, false);

// Notify whenever the text selection changes
document.addEventListener('selectionchange', function () {
                          var json = { 'action': 'selectionChanged', 'showMenu': (document.getSelection().rangeCount > 0), 'rect': LDS.gospelLibrary.selectionRect(), 'aids': LDS.gospelLibrary.getAIDsForSelection() };
                          window.webkit.messageHandlers.handler.postMessage(json);
}, false);
