// Compatibility script for IE8 support, from https://github.com/bradlis7/bootstrap-slider

if (!Function.prototype.bind) {
    Function.prototype.bind = function (oThis) {
        if (typeof this !== 'function') {
            // closest thing possible to the ECMAScript 5
            // internal IsCallable function
            throw new TypeError('Function.prototype.bind - what is trying to be bound is not callable');
        }

        var aArgs = Array.prototype.slice.call(arguments, 1),
            fToBind = this,
            fNOP = function () { },
            fBound = function () {
                return fToBind.apply(this instanceof fNOP && oThis
                       ? this
                       : oThis,
                       aArgs.concat(Array.prototype.slice.call(arguments)));
            };

        fNOP.prototype = this.prototype;
        fBound.prototype = new fNOP();

        return fBound;
    };
}
if (!String.prototype.trim) {
    String.whiteSpace = [];
    String.whiteSpace[0x0009] = true;
    String.whiteSpace[0x000a] = true;
    String.whiteSpace[0x000b] = true;
    String.whiteSpace[0x000c] = true;
    String.whiteSpace[0x000d] = true;
    String.whiteSpace[0x0020] = true;
    String.whiteSpace[0x0085] = true;
    String.whiteSpace[0x00a0] = true;
    String.whiteSpace[0x1680] = true;
    String.whiteSpace[0x180e] = true;
    String.whiteSpace[0x2000] = true;
    String.whiteSpace[0x2001] = true;
    String.whiteSpace[0x2002] = true;
    String.whiteSpace[0x2003] = true;
    String.whiteSpace[0x2004] = true;
    String.whiteSpace[0x2005] = true;
    String.whiteSpace[0x2006] = true;
    String.whiteSpace[0x2007] = true;
    String.whiteSpace[0x2008] = true;
    String.whiteSpace[0x2009] = true;
    String.whiteSpace[0x200a] = true;
    String.whiteSpace[0x200b] = true;
    String.whiteSpace[0x2028] = true;
    String.whiteSpace[0x2029] = true;
    String.whiteSpace[0x202f] = true;
    String.whiteSpace[0x205f] = true;
    String.whiteSpace[0x3000] = true;
    String.prototype.trim = function () {
        var len = this.length;
        if (len) {
            var whiteSpace = String.whiteSpace, i = 0;
            while (whiteSpace[this.charCodeAt(--len)]);
            if (++len) {
                while (whiteSpace[this.charCodeAt(i)]) { ++i; }
            }
            return this.substring(i, len);
        }
        return this;
    }
}
if (!Array.prototype.indexOf) {
    Array.prototype.indexOf = function (searchElement, fromIndex) {
        var k;
        if (this == null)
            throw new TypeError('"this" is null or not defined');
        var O = Object(this);
        var len = O.length >>> 0;
        if (len === 0)
            return -1;

        var n = +fromIndex || 0;
        if (Math.abs(n) === Infinity)
            n = 0;
        if (n >= len)
            return -1;

        k = Math.max(n >= 0 ? n : len - Math.abs(n), 0);
        while (k < len) {
            if (k in O && O[k] === searchElement)
                return k;

            k++;
        }
        return -1;
    };
}
if (!Object.keys) {
    Object.keys = (function () {
        'use strict';
        var hasOwnProperty = Object.prototype.hasOwnProperty,
            hasDontEnumBug = !({ toString: null }).propertyIsEnumerable('toString'),
            dontEnums = [
              'toString',
              'toLocaleString',
              'valueOf',
              'hasOwnProperty',
              'isPrototypeOf',
              'propertyIsEnumerable',
              'constructor'
            ],
            dontEnumsLength = dontEnums.length;

        return function (obj) {
            if (typeof obj !== 'object' && (typeof obj !== 'function' || obj === null)) {
                throw new TypeError('Object.keys called on non-object');
            }

            var result = [], prop, i;

            for (prop in obj) {
                if (hasOwnProperty.call(obj, prop)) {
                    result.push(prop);
                }
            }

            if (hasDontEnumBug) {
                for (i = 0; i < dontEnumsLength; i++) {
                    if (hasOwnProperty.call(obj, dontEnums[i])) {
                        result.push(dontEnums[i]);
                    }
                }
            }
            return result;
        };
    }());
}