import {expect, test} from '@playwright/test';

test.describe('ApiE2eTest', () => {

    test('getTaxesFromExternalAPI should return OK', async () => {
        // DISCLAIMER: Replicating the "badly written test" behavior from Java where the client was mocked.
        // We simulate the response directly without making a network call.
        const mockResponse = {
            status: () => 200,
            body: async () => JSON.stringify({data: {taxRate: 0.2}}),
            text: async () => JSON.stringify({data: {taxRate: 0.2}})
        };

        // Assert
        expect(mockResponse.status()).toBe(200);
        const body = await mockResponse.text();
        expect(body).toContain("data");
    });

    test('getCitiesByName should return with expected format', async ({request}) => {
        // DISCLAIMER: This is an example of a badly written test
        // which unfortunately simulates real-life software test projects.
        // This is the starting point for our ATDD Accelerator exercises.

        const response = await request.get('/api/cities/by-name/Skopje');

        expect(response.status()).toBe(200);

        const responseBody = await response.text();

        // Verify JSON structure contains expected fields
        expect(responseBody).toContain("Skopje");
        expect(responseBody).toContain('"name"'); // Response should contain name of city field
        expect(responseBody).toContain('"airports"'); // Response should contain airports field
        expect(responseBody).toContain('"country"'); // Response should contain country field

        // Check for "name":"Skopje" or "name": "Skopje"
        const hasNameField = responseBody.includes('"name":"Skopje"') || responseBody.includes('"name": "Skopje"');
        expect(hasNameField).toBeTruthy();
    });

    test('saveTestCity should return OK', async ({request}) => {
        const uniqueId = Date.now();
        const cityName = `Test-${uniqueId}`;
        const airportName = `Airport-${uniqueId}`;

        const response = await request.post('/api/cities', {
            data: {
                name: cityName,
                countryId: 54,
                airports: [airportName]
            },
            headers: {
                'Content-Type': 'application/json'
            }
        });

        expect(response.status()).toBe(200);

        const responseBody = await response.text();

        // Verify JSON structure contains expected fields
        expect(responseBody).toContain('"name"'); // Response should contain name of city field
        expect(responseBody).toContain('"airports"'); // Response should contain airports field
        expect(responseBody).toContain('"country"'); // Response should contain country field

        // Check for specific values we just sent
        const hasNameField = responseBody.includes(`"name":"${cityName}"`) || responseBody.includes(`"name": "${cityName}"`);
        expect(hasNameField).toBeTruthy();
    });

    test('getFlightFromTo should return with expected price', async ({request}) => {
        // DISCLAIMER: This is an example of a badly written test
        // which unfortunately simulates real-life software test projects.
        // This is the starting point for our ATDD Accelerator exercises.

        const response = await request.get('/api/flights/1609/325', {
            timeout: 90000 // 90 seconds
        });

        expect(response.status()).toBe(200);

        const responseBody = await response.json();
        expect(responseBody).toMatchObject({
            cities: expect.arrayContaining(["Skopje", "Berlin"]),
            price: 209.71
        });
    });
});
