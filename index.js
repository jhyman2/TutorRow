/* APPLICATION ENTRY */
require('@babel/register');
require('regenerator-runtime/runtime');
require('dotenv').config();
require('./src/server/app.js');