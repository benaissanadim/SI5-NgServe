const http = require('http');

let preparationFromTable = [];

function getPreparationStarted() {
  return new Promise((resolve, reject) => {
    http.get(`http://localhost:3002/preparations?state=preparationStarted`, (response) => {
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

function getMakeCooking(id) {
  return new Promise((resolve, reject) => {
    const options = {
      method: 'POST',
      hostname: 'localhost',
      port: 3002,
      path: `/preparedItems/${id}/start`,
    };

    const req = http.request(options, (response) => {
      response.on('data', (chunk) => {
        const data = JSON.parse(chunk);
        console.log(`Response from makeCooking for item with ID ${id}:`, data);
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

function getEndCooking(id) {
  return new Promise((resolve, reject) => {
    const options = {
      method: 'POST',
      hostname: 'localhost',
      port: 3002,
      path: `/preparedItems/${id}/finish`,
    };

    const req = http.request(options, (response) => {
      response.on('data', (chunk) => {
        const data = JSON.parse(chunk);
        console.log(`Response from end cooking for item with ID ${id}:`, data);
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
      for (const item of table.preparedItems) {
        console.log("item " + item.id);
        try {
          await sleep(100); 
          await getMakeCooking(item.id);
          console.log(`Start cooking for item with ID: ${item.id}`);
        } catch (makeCookingError) {
          console.error(`Error starting cooking for item with ID: ${item.id}`, makeCookingError);
        }
      }
      for (const item of table.preparedItems) {
        console.log("item " + item.id);
        try {
          await sleep(100); 
          await getEndCooking(item.id);
          console.log(`Ending cooking for item with ID: ${item.id}`);
        } catch (endCookingError) {
          console.error(`Error ending cooking for item with ID: ${item.id}`, endCookingError);
        }
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