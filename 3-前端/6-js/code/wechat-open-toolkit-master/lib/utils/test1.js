const express = require('express');
const bodyParser = require('body-parser');
const axios = require('axios');
const xml2js = require('xml2js');

const app = express();
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.text());

const token = 'xxxxxxxxxxx';
const encodingAesKey = 'xxxxxxxxxxxxxxxx';
const appid = 'xxxxxxxx';
const appsecret = 'xxxxxxxxxxxxxx';

app.post('/receiveEvent', async (req, res) => {
    const authorizer_appid = req.query.appid;
    const postStr = req.body;

    if (postStr) {
        const parser = new xml2js.Parser();
        parser.parseString(postStr, (err, result) => {
            if (err) {
                console.error(err);
                return res.send('success');
            }

            const toUserName = result.xml.ToUserName[0];
            const encrypt = result.xml.Encrypt[0];
            const from_xml = `<xml><Encrypt><![CDATA[${encrypt}]]></Encrypt></xml>`;

            const inputs = {
                encrypt_type: req.query.encrypt_type || '',
                timestamp: req.query.timestamp || '',
                nonce: req.query.nonce || '',
                msg_signature: req.query.msg_signature || '',
                signature: req.query.signature || ''
            };

            const msg = '';
            const timeStamp = inputs.timestamp;
            const msg_sign = inputs.msg_signature;
            const nonce = inputs.nonce;

            const WXBizMsgCrypt = require('minicrypto').WXBizMsgCrypt;
            const pc = new WXBizMsgCrypt(token, encodingAesKey, appid);
            const errCode = pc.decryptMsg(msg_sign, timeStamp, nonce, from_xml, (err, msg) => {
                if (err) {
                    console.error(err);
                    return res.send('success');
                }

                parser.parseString(msg, (err, msgObj) => {
                    if (err) {
                        console.error(err);
                        return res.send('success');
                    }

                    const content = msgObj.xml.Content[0];

                    if (msgObj.xml.MsgType[0].toLowerCase() === 'text' && content === 'TESTCOMPONENT_MSG_TYPE_TEXT') {
                        if (toUserName === 'gh_3c884a361561') {
                            const responseContent = 'TESTCOMPONENT_MSG_TYPE_TEXT_callback';
                            res.send(responseText(msgObj, responseContent));
                        }
                    }

                    if (content.includes('QUERY_AUTH_CODE')) {
                        if (toUserName === 'gh_3c884a361561') {
                            const query_auth_code = content.replace('QUERY_AUTH_CODE:', '');
                            const params = dedeLogic.api_query_auth(query_auth_code);
                            const authorizer_access_token = params.authorization_info.authorizer_access_token;
                            const responseContent = `${query_auth_code}_from_api`;
                            sendServiceText(msgObj, responseContent, authorizer_access_token);
                        }
                    }
                });
            });

            res.send('success');
        });
    } else {
        res.send('success');
    }
});

function responseText(object, content) {
    if (!content) {
        return '';
    }
    const xmlTpl = `
        <xml>
            <ToUserName><![CDATA[%s]]></ToUserName>
            <FromUserName><![CDATA[%s]]></FromUserName>
            <CreateTime>%d</CreateTime>
            <MsgType><![CDATA[text]]></MsgType>
            <Content><![CDATA[%s]]></Content>
        </xml>
    `;
    return sprintf(xmlTpl, object.FromUserName[0], object.ToUserName[0], Date.now(), content);
}

function sendServiceText(object, content, access_token) {
    const openid = object.FromUserName[0];
    const postData = {
        touser: openid,
        msgtype: 'text',
        text: {
            content: content
        }
    };
    sendMessages(postData, access_token);
}

async function sendMessages(postData, access_token) {
    const url = `https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=${access_token}`;
    try {
        await axios.post(url, postData, {
            headers: {
                'Content-Type': 'application/json'
            }
        });
    } catch (error) {
        console.error(error);
    }
}

function httpRequest(url, method = 'GET', postfields = null, headers = [], debug = false) {
    const options = {
        method: method,
        url: url,
        headers: headers
    };

    if (method === 'POST' && postfields) {
        options.data = postfields;
    }

    return axios(options)
        .then(response => {
            if (debug) {
                console.log("=====post data======");
                console.log(postfields);
                console.log("=====info======");
                console.log(response.config);
                console.log("=====response======");
                console.log(response.data);
            }
            return response.data;
        })
        .catch(error => {
            if (debug) {
                console.error("=====error======");
                console.error(error);
            }
            throw error;
        });
}

app.listen(3000, () => {
    console.log('Server is running on port 3000');
});