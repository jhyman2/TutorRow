export default ({ body, title, initialState }) => {
  return `
    <!DOCTYPE html>
    <html>
      <head>
        <script>window.__APP_INITIAL_STATE__ = ${initialState}</script>
        <title>${title}</title>
      </head>

      <body>
        <div id="root">${process.env.NODE_ENV === 'production' ? body : `<div>${body}</div>`}</div>
      </body>

      <script src="/bundle.js"></script>
    </html>
  `;
};
