
const http = require('http');

let preparationFromTable = [];

function getPreparationStarted() {
  return new Promise((resolve, reject) => {
    http.get(`http://localhost:3002/preparations?state=readyToBeServed`, (response) => {
      let data = '';
      response.on('data', (chunk) => {
        data += chunk;
      });

      response.on('end', () => {
        preparationFromTable = JSON.parse(data);
        resolve(preparationFromTable);
      });

      response.on('error', (err) => {
        reject(err);
      });
    });
  });
}

function take(id) {
  return new Promise((resolve, reject) => {
    const options = {
      method: 'POST',
      hostname: 'localhost',
      port: 3002,
      path: `/preparations/${id}/takenToTable`,
    };

    const req = http.request(options, (response) => {
      response.on('data', (chunk) => {
        const data = JSON.parse(chunk);
        console.log(`Response from taking for item with ID ${id}:`, data);
      });

      response.on('end', () => {
        resolve();
      });

      response.on('error', (err) => {
        reject(err);
      });
    });

    req.end();
  });
}

async function main() {
  try {
    await getPreparationStarted();
    console.log('Retrieved data:', preparationFromTable);
    
    for (const table of preparationFromTable) {
        try {
          await sleep(100); 
          await take(table.id);
          console.log(`take for  ID: ${table.id}`);
        } catch (makeCookingError) {
          console.error(`Error taking with ID: ${table.id}`, makeCookingError);
        }
    }
   
  } catch (err) {
    console.error('An error occurred:', err);
  }
}

main();

async function sleep(ms) {
  return new Promise(resolve => setTimeout(resolve, ms));
}