const xml2js = require('xml2js');

// 配置解析器、构建器
const xmlParser = new xml2js.Parser({
    explicitRoot: false,
    explicitArray: false
});

const xmlBuilder = new xml2js.Builder({
    rootName: 'xml',
    headless: true,
    cdata: true
});

// 绑定解析和构建的方法
const parseXml = xmlParser.parseString.bind(xmlParser);
const buildObject = xmlBuilder.buildObject.bind(xmlBuilder);

/**
 * 解析 XML 数据
 * @param {string} str - 要解析的 XML 字符串
 * @returns {Promise<object>} 解析后的 JavaScript 对象
 */
export function parseXMLSync(str) {
    return new Promise((resolve, reject) => {
        parseXml(str, (err, result) => {
            if (err) {
                reject(new Error(`解析 XML 时出错: ${err.message}`));
            } else {
                resolve(result);
            }
        });
    });
}

/**
 * 构建 XML 字符串
 * @param {object} obj - 要构建的 JavaScript 对象
 * @returns {string} 构建的 XML 字符串
 */
export function buildXMLSync(obj = {}) {
    try {
        const xmlString = buildObject(obj); // 使用绑定的方法构建 XML
        return xmlString; // 解析成功，返回 XML 字符串
    } catch (err) {
        throw new Error(`构建 XML 时出错: ${err.message}`);
    }
}
