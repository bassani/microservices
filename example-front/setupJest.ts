import 'jest-preset-angular/setup-jest'


const noop = () => {
};

Object.defineProperty(window, 'CSS', {value: null});
Object.defineProperty(window, 'getComputedStyle', {
    value: () => {
        return {
            display: 'none',
            appearance: ['-webkit-appearance']
        };
    }
});

Object.defineProperty(document, 'doctype', {
    value: '<!DOCTYPE html>'
});
Object.defineProperty(document.body.style, 'transform', {
    value: () => {
        return {
            enumerable: true,
            configurable: true
        };
    }
});

Object.defineProperty(window, 'getComputedStyle', {
    value: () => ({
        getPropertyValue: (prop: any) => {
            return prop || '';
        }
    })
});

Object.defineProperty(window, 'scrollTo', {value: noop, writable: true});
