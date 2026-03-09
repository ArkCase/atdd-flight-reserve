import { expect, test } from '@playwright/test';

test.describe('API Negative E2E Tests - Invalid Input Types', () => {

    test('GET /api/routes - should return 400 when srcAirportId has invalid type', async ({ request }) => {
        // Given: Invalid source airport ID (string instead of integer)
        const invalidSrcAirportId = 'invalid-string';
        const validDstAirportId = 123;

        // When: Making request with invalid type
        const response = await request.get(`/api/routes/by/${invalidSrcAirportId}/${validDstAirportId}`);

        // Then: Should return 400 Bad Request
        expect(response.status()).toBe(400);
    });

    test('GET /api/routes - should return 400 when dstAirportId has invalid type', async ({ request }) => {
        // Given: Invalid destination airport ID (string instead of integer)
        const validSrcAirportId = 123;
        const invalidDstAirportId = 'not-a-number';

        // When: Making request with invalid type
        const response = await request.get(`/api/routes/by/${validSrcAirportId}/${invalidDstAirportId}`);

        // Then: Should return 400 Bad Request
        expect(response.status()).toBe(400);
    });

    test('GET /api/routes - should return error when both airport IDs have invalid types', async ({ request }) => {
        // Given: Both IDs are invalid types (special characters)
        const invalidSrcAirportId = '@#$%';
        const invalidDstAirportId = '!@#$';

        // When: Making request with invalid types
        const response = await request.get(`/api/routes/by/${invalidSrcAirportId}/${invalidDstAirportId}`);

        // Then: Should return 400 Bad Request or 404 Not Found
        expect([400, 404]).toContain(response.status());
    });

    test('GET /api/flights - should return 400 when source city ID has invalid type', async ({ request }) => {
        // Given: Invalid source city ID (alphabetic string)
        const invalidSourceId = 'abc';
        const validDestinationId = 325;

        // When: Making request with invalid type
        const response = await request.get(`/api/flights/${invalidSourceId}/${validDestinationId}`);

        // Then: Should return 400 Bad Request
        expect(response.status()).toBe(400);
    });

    test('GET /api/flights - should return 400 when destination city ID has invalid type', async ({ request }) => {
        // Given: Invalid destination city ID (alphanumeric mix)
        const validSourceId = 1609;
        const invalidDestinationId = 'xyz123abc';

        // When: Making request with invalid type
        const response = await request.get(`/api/flights/${validSourceId}/${invalidDestinationId}`);

        // Then: Should return 400 Bad Request
        expect(response.status()).toBe(400);
    });

    test('GET /api/airports - should return 400 when cityId has invalid type', async ({ request }) => {
        // Given: Invalid city ID (boolean-like string)
        const invalidCityId = 'true';
        const validCountryId = 54;

        // When: Making request with invalid type
        const response = await request.get(`/api/airports/by/${invalidCityId}/${validCountryId}`);

        // Then: Should return 400 Bad Request
        expect(response.status()).toBe(400);
    });

    test('GET /api/airports - should return 400 when countryId has invalid type', async ({ request }) => {
        // Given: Invalid country ID (decimal number as string)
        const validCityId = 1609;
        const invalidCountryId = '54.5';

        // When: Making request with invalid type
        const response = await request.get(`/api/airports/by/${validCityId}/${invalidCountryId}`);

        // Then: Should return 400 Bad Request
        expect(response.status()).toBe(400);
    });

    test('GET /api/cities/search - should return 400 when countryId query param has invalid type', async ({ request }) => {
        // Given: Invalid countryId in query parameters (non-numeric string)
        const invalidCountryId = 'not-a-number';
        const query = 'Skopje';

        // When: Making request with invalid query parameter type
        const response = await request.get(`/api/cities/search?countryId=${invalidCountryId}&query=${query}&limit=10`);

        // Then: Should return 400 Bad Request
        expect(response.status()).toBe(400);
    });

    test('GET /api/cities/search - should return 400 when limit query param has invalid type', async ({ request }) => {
        // Given: Invalid limit in query parameters (alphabetic string)
        const validCountryId = 54;
        const query = 'Skopje';
        const invalidLimit = 'infinite';

        // When: Making request with invalid query parameter type
        const response = await request.get(`/api/cities/search?countryId=${validCountryId}&query=${query}&limit=${invalidLimit}`);

        // Then: Should return 400 Bad Request
        expect(response.status()).toBe(400);
    });

    test('POST /api/cities - should return 400 when countryId in body has invalid type', async ({ request }) => {
        // Given: Invalid countryId in request body (string instead of number)
        const cityData = {
            name: 'Test City',
            countryId: 'not-a-number',
            airports: ['Test Airport']
        };

        // When: Making POST request with invalid data type
        const response = await request.post('/api/cities', {
            data: cityData,
            headers: {
                'Content-Type': 'application/json'
            }
        });

        // Then: Should return 400 Bad Request
        expect(response.status()).toBe(400);
    });

    test('POST /api/cities - should return 400 when request body is malformed JSON', async ({ request }) => {
        // Given: Malformed JSON body
        const malformedJson = '{ "name": "Test", "countryId": "not-closed"';

        // When: Making POST request with malformed JSON
        const response = await request.post('/api/cities', {
            data: malformedJson,
            headers: {
                'Content-Type': 'application/json'
            }
        });

        // Then: Should return 400 Bad Request
        expect(response.status()).toBe(400);
    });

    test('DELETE /api/cities - should return 400 when city ID path param has invalid type', async ({ request }) => {
        // Given: Invalid city ID (non-numeric string with special chars)
        const invalidCityId = 'delete-me!';

        // When: Making DELETE request with invalid type
        const response = await request.delete(`/api/cities/${invalidCityId}`);

        // Then: Should return 400 Bad Request  
        expect(response.status()).toBe(400);
    });

    test('GET /api/routes - should return 400 when airport ID contains SQL injection attempt', async ({ request }) => {
        // Given: Malicious input attempting SQL injection
        const maliciousInput = "1' OR '1'='1";
        const validDstAirportId = 123;

        // When: Making request with malicious input
        const response = await request.get(`/api/routes/by/${encodeURIComponent(maliciousInput)}/${validDstAirportId}`);

        // Then: Should return 400 Bad Request (type mismatch)
        expect(response.status()).toBe(400);
    });

    test('GET /api/routes - should handle gracefully when airport ID is negative number', async ({ request }) => {
        // Given: Negative number as airport ID (valid integer type, but likely no data)
        const negativeSrcAirportId = '-999';
        const validDstAirportId = 123;

        // When: Making request with negative ID
        const response = await request.get(`/api/routes/by/${negativeSrcAirportId}/${validDstAirportId}`);

        // Then: Should handle gracefully (200 with empty array, or 404/400)
        expect([200, 400, 404]).toContain(response.status());
        
        if (response.status() === 200) {
            const body = await response.json();
            expect(Array.isArray(body)).toBeTruthy();
        }
    });

    test('GET /api/routes - should return 400 when airport ID exceeds integer max value', async ({ request }) => {
        // Given: Number exceeding Integer.MAX_VALUE
        const oversizedId = '999999999999999999999';
        const validDstAirportId = 123;

        // When: Making request with oversized number
        const response = await request.get(`/api/routes/by/${oversizedId}/${validDstAirportId}`);

        // Then: Should return 400 Bad Request
        expect(response.status()).toBe(400);
    });
});
