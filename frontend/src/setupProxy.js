const proxy = require('http-proxy-middleware');
module.exports = function(app) {
    app.use(proxy('/api', { 
        target: 'http://localhost:8228/',
        headers: {
            Auth: 'secret',
            userId: 'f63e3113-9583-47ee-9a8e-809f9077e6f7'
        },
        ws: false
    }));
}