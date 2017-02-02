export default ({ body, title, initialState }) => {
  return `
    <!DOCTYPE html>
    <html>
      <head>
        <script>window.__APP_INITIAL_STATE__ = ${initialState}</script>
        <title>${title}</title>
        <link rel="stylesheet" href="/styles.css" />
      </head>

      <body>
        <div id="root">${process.env.NODE_ENV === 'production' ? body : `<div>${body}</div>`}</div>
      </body>

      <script src="/select_university.js"></script>
    </html>
  `;
};
