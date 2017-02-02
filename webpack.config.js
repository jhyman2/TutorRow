module.exports = {
  entry: {
    bundle: './src/server/views/components/login/browser.js',
    main: './src/server/views/components/main_app/browser.js',
    select_university: './src/server/views/components/select_university/browser.js'
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
    filename: '[name].js'
  },
  devServer: {
    contentBase: './dist'
  }
}