global.localStorage = {
    _data: {},
    setItem(key, value) {
      this._data[key] = value.toString();
    },
    getItem(key) {
      return this._data.hasOwnProperty(key) ? this._data[key] : null;
    },
    removeItem(key) {
      delete this._data[key];
    },
    clear() {
      this._data = {};
    },
  };
  