module.exports = {
  entry: {
    bundle: './src/server/views/components/login/browser.js',
    main: ['babel-polyfill', './src/client/main_app/browser.js']
  },
  module: {
    rules: [
      {
        test: /\.jsx?$/,
        exclude: /node_modules/,
        loader: 'babel-loader',
        query: {
          presets: ['react', 'es2015', 'stage-0']
        }
      },
      {
        test: /\.css$/,
        use: [
          'style-loader',
          'css-loader'
        ]
      },
      {
        test: /\.(png|svg|jpg|gif)$/,
        use: [
          'file-loader'
        ]
      }
    ]
  },
  resolveLoader: {
    modules: [
        './node_modules'
    ]
  },
  output: {
    path: __dirname + '/dist',
    filename: '[name].js'
  },
  devServer: {
    contentBase: './dist',
    color: true
  }
}