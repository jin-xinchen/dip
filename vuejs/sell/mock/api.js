const fs = require('fs');

function fromJSONFile(filename) {
    return (req, res) => {
        const data = fs.readFileSync(`mock/data/${filename}.json`).toString();
        const json = JSON.parse(data);
			return res.json(json);
		};
	}

function getData(){
  return (req, res) => {
  	const data = fs.readFileSync(`mock/data/data.json`).toString();
  	const json = JSON.parse(data);
    let result = {'data':json.goods,'error':0}
  	return res.json(result);
  };
}

	const proxy = {
		  'GET /api/seller': fromJSONFile('seller'),
	    'GET /api/goods': getData(),
      'GET /api/user': {id: 1, username: 'kenny', sex: 6 },
      'GET /api/user/list': [{id: 1, username:'dataenny', sex: 6 },
      {id: 2, username: 'kenny', sex: 6 }
      ],
      'POST /api/login/account': (req, res) => {
        const { password, username } = req.body;
        if (password === '888888' && username === 'admin') {
          return res.send({
            status: 'ok',
            code: 0,
            token: "sdfsdfsdfdsf",
            data: {id: 1, username: 'kenny', sex: 6 }
          });
        } else {
          return res.send({status: 'error', code: 403 });
        }
      },
      'DELETE /api/user/:id': (req, res) => {
        console.log('---->', req.body)
        console.log('---->', req.params.id)
        res.send({ status: 'ok', message: '删除成功！' });
      }
};
module.exports = proxy;
