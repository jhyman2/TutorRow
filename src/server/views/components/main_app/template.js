export default ({ title, initialState }) => {
  return `
    <!DOCTYPE html>
    <html>
      <head>
        <script>window.__APP_INITIAL_STATE__ = ${initialState}</script>
        <title>${title}</title>
      </head>

      <body>
        <div id="root"></div>
      </body>

      <script src="/main.js"></script>
    </html>
  `;
};