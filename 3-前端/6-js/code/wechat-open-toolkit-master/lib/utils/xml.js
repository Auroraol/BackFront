const xml2js = require('xml2js')

const xmlParser = new xml2js.Parser({
    explicitRoot: false,
    explicitArray: false
})

// 解板XML数据
export function parseXMLSync(str) {
    return new Promise(function (resolve, reject) {
        xmlParser.parseString(str, function (err, result) {
            if (err) {
                reject(err)
            } else {
                resolve(result)
            }
        })
    })
}