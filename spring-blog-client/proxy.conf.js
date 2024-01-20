const PROXY_CONFIG = [
    {
        context: ['/api'],
        target: 'http://app:8081/',
        secure: false,
        logLevel: 'debug',
       /*  pathRewrite: {'^/api':''} */
    }
];

module.exports = PROXY_CONFIG
