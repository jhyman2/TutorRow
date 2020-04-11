export default ({ body, title, initialState }) => {
  return `
    <!DOCTYPE html>
    <html>
      <head>
        <title>${title}</title>
      </head>

      <body>
        <div id="root">
          <h1>${initialState.error}</h1>
          <h5>${initialState.instructions}</h5>
        </div>
      </body>
    </html>
  `;
};
