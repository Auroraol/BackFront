'use strict'

process.env.BABEL_ENV = 'renderer'

const path = require('path')
const { dependencies } = require('../package.json')
const webpack = require('webpack')

const CopyWebpackPlugin = require('copy-webpack-plugin')
const ExtractTextPlugin = require('extract-text-webpack-plugin')
const HtmlWebpackPlugin = require('html-webpack-plugin')

/**
 * List of node_modules to include in webpack bundle
 *
 * Required for specific packages like Vue UI libraries
 * that provide pure *.vue files that need compiling
 * https://simulatedgreg.gitbooks.io/electron-vue/content/en/webpack-configurations.html#white-listing-externals
 */
let whiteListedModules = ['vue']

const scss_loader = [
    'vue-style-loader',
    'css-loader',
    'sass-loader',
    {
        loader: 'sass-resources-loader',
        options: {
            resources: [
                path.join(__dirname, '../src/renderer/assets/variable.scss'),
                path.join(__dirname, '../src/renderer/assets/mixin.scss'),
            ],
        },
    },
]
let rendererConfig = {
    devtool: '#cheap-module-eval-source-map',
    entry: {
        background: path.join(__dirname, '../src/background/main.js'),
    },
    externals: [
        ...Object.keys(dependencies || {}).filter(
            d => !whiteListedModules.includes(d)
        ),
    ],
    module: {
        rules: [
            {
                test: /\.css$/,
                use: ExtractTextPlugin.extract({
                    fallback: 'style-loader',
                    use: 'css-loader',
                }),
            },
            {
                test: /\.html$/,
                use: 'vue-html-loader',
            },
            {
                test: /\.js$/,
                use: 'babel-loader',
                exclude: /node_modules/,
            },
            {
                test: /\.node$/,
                use: 'node-loader',
            },
            {
                test: /\.scss$/,
                use: scss_loader,
            },
            {
                test: /\.vue$/,
                use: {
                    loader: 'vue-loader',
                    options: {
                        extractCSS: process.env.NODE_ENV === 'production',
                        loaders: {
                            sass:
                                'vue-style-loader!css-loader!sass-loader?indentedSyntax=1',
                            scss: scss_loader,
                        },
                        cssModules: {
                            localIdentName: '[local]-[hash:base64:5]',
                            camelCase: true,
                        },
                    },
                },
            },
            {
                test: /\.(png|jpe?g|gif|svg)(\?.*)?$/,
                use: {
                    loader: 'url-loader',
                    query: {
                        limit: 10000,
                        name: 'imgs/[name]--[folder].[ext]',
                    },
                },
            },
            {
                test: /\.(mp4|webm|ogg|mp3|wav|flac|aac)(\?.*)?$/,
                loader: 'url-loader',
                options: {
                    limit: 10000,
                    name: 'media/[name]--[folder].[ext]',
                },
            },
            {
                test: /\.(woff2?|eot|ttf|otf)(\?.*)?$/,
                use: {
                    loader: 'url-loader',
                    query: {
                        limit: 10000,
                        name: 'fonts/[name]--[folder].[ext]',
                    },
                },
            },
        ],
    },
    node: {
        __dirname: process.env.NODE_ENV !== 'production',
        __filename: process.env.NODE_ENV !== 'production',
    },
    plugins: [
        new webpack.ProvidePlugin({
            Vue: ['vue/dist/vue.esm.js', 'default'],
        }),
        new ExtractTextPlugin('background-styles.css'),
        new HtmlWebpackPlugin({
            filename: 'background.html',
            template: path.resolve(__dirname, '../src/index.ejs'),
            minify: {
                collapseWhitespace: true,
                removeAttributeQuotes: true,
                removeComments: true,
            },
            nodeModules:
                process.env.NODE_ENV !== 'production'
                    ? path.resolve(__dirname, '../node_modules')
                    : false,
        }),
        new webpack.HotModuleReplacementPlugin(),
        new webpack.NoEmitOnErrorsPlugin(),
    ],
    output: {
        filename: '[name].[hash].js',
        libraryTarget: 'commonjs2',
        path: path.join(__dirname, '../dist/electron'),
    },
    resolve: {
        alias: {
            '@': path.join(__dirname, '../src/background'),
            src: path.join(__dirname, '../src'),
            vue$: 'vue/dist/vue.esm.js',
        },
        extensions: ['.js', '.vue', '.json', '.css', '.node'],
    },
    target: 'electron-renderer',
}

/**
 * Adjust rendererConfig for development settings
 */
if (process.env.NODE_ENV !== 'production') {
    rendererConfig.plugins.push(
        new webpack.DefinePlugin({
            __static: `"${path
                .join(__dirname, '../static')
                .replace(/\\/g, '\\\\')}"`,
        })
    )
}

/**
 * Adjust rendererConfig for production settings
 */
if (process.env.NODE_ENV === 'production') {
    rendererConfig.devtool = ''

    rendererConfig.plugins.push(
        new CopyWebpackPlugin([
            {
                from: path.join(__dirname, '../static'),
                to: path.join(__dirname, '../dist/electron/static'),
                ignore: ['.*'],
            },
        ]),
        new webpack.DefinePlugin({
            'process.env.NODE_ENV': '"production"',
        }),
        new webpack.LoaderOptionsPlugin({
            minimize: true,
        })
    )
}

module.exports = rendererConfig
