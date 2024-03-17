const http = require('http');

// Fetch the table number from the command-line arguments
const tableNumber = process.argv[2];

if (!tableNumber) {
  console.error('Please provide a table number as a command-line argument.');
  process.exit(1);
}

console.log(`Liberating table ${tableNumber}...`);

// Function to perform GET request
function getTableInfo() {
  return new Promise((resolve, reject) => {
    http.get(`http://localhost:3001/tables/${tableNumber}`, (response) => {
      let data = '';

      response.on('data', (chunk) => {
        data += chunk;
      });

      response.on('end', () => {
        resolve(JSON.parse(data));
      });
    }).on('error', (err) => {
      reject(err);
    });
  });
}

// Function to perform POST request
function postBill(tableOrderId) {
  const options = {
    hostname: 'localhost',
    port: 3001,
    path: `/tableOrders/${tableOrderId}/bill`,
    method: 'POST'
  };

  const req = http.request(options, (res) => {
    console.log(`statusCode: ${res.statusCode}`);
    if(res.statusCode === 200) {
      console.log(`POST request sent to bill the order ${tableOrderId}`);
    }
  });

  req.on('error', (error) => {
    console.error(error);
  });

  req.end();
}

// Main execution
getTableInfo().then((tableInfo) => {
  if (tableInfo && tableInfo.tableOrderId) {
    postBill(tableInfo.tableOrderId);
  } else {
    console.error('Error: tableOrderId not found.');
  }
}).catch((err) => {
  console.error('An error occurred:', err);
});
