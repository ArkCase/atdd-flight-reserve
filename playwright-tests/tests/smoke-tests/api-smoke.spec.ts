import { test, expect } from '@playwright/test';

test.describe('ApiSmokeTest', () => {

    test('echo should return 200 OK', async ({ request }) => {
        // DISCLAIMER: This is an example of a badly written test
        // which unfortunately simulates real-life software test projects.
        // This is the starting point for our ATDD Accelerator exercises.

        const response = await request.get('/api/echo');

        expect(response.status()).toBe(200);
    });
});
