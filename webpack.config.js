module.exports = {
  entry: {
    app: './src/server/views/components/browser.js'
  },
  progress: true,
  colors: true,
  module: {
    loaders: [{
      test: /\.jsx?$/,
      exclude: /node_modules/,
      loader: 'babel',
      query: {
        presets: ['react', 'es2015', 'stage-0']
      }
    }]
  },
  resolve: {
    extensions: ['', '.js', '.html']
  },
  resolveLoader: {
    modulesDirectories: [
        './node_modules'
    ]
  },
  output: {
    path: __dirname + '/dist',
    filename: 'bundle.js'
  },
  devServer: {
    contentBase: './dist'
  }
}