# Playwright Tests

This directory contains automated UI tests for the Flight Reservation System using Playwright.

## Structure

```
playwright-tests/
├── pages/                # Page Object Models (Reusable UI logic)
│   ├── basePage.ts       # Common base class
│   └── cityManagementPage.ts
├── tests/
│   └── ui/               # UI/Frontend tests
│       └── crud/         # CRUD operation tests
└── fixtures/             # Test data and fixtures
```

## Test Categories

### UI Tests (`tests/ui/`)

UI tests verify frontend functionality and user interactions using the Page Object Model pattern.

## Page Object Model Pattern

UI tests should follow the Page Object Model (POM) pattern to keep tests maintainable and reusable. Page Objects are located in the `pages/` directory.

### Example Usage

```typescript
import { test, expect } from '@playwright/test';
import { CityManagementPage } from '../../pages/cityManagementPage';

test('should create a city', async ({ page }) => {
    const cityPage = new CityManagementPage(page);
    await cityPage.navigateToCityManagement();
    await cityPage.addCity('Skopje', 'Macedonia');
});
```

## Running Tests

```bash
# Run all UI tests
npx playwright test

# Run with headed browser (see tests in action)
npx playwright test --headed

# Run with UI mode (interactive)
npx playwright test --ui

# Run specific test
npx playwright test tests/ui/crud/city-management.spec.ts

# Debug mode
npx playwright test --debug

# Generate HTML report
npm run test:report
```

## Best Practices

1. **Use Page Objects** - Encapsulate page interactions in Page Object classes in the `pages/` directory.
2. **Use Robust Selectors** - Prefer specific IDs, data attributes, or precise class names over strict text matching.
3. **Wait for Elements** - Rely on Playwright's auto-waiting, but use explicit waits for complex animations (e.g., Material Angular notifications).
4. **Independent Tests** - Each test should handle its own data setup and cleanup.
5. **No Console Logs** - Keep test output clean.

## Configuration

- **Base URL:** `http://localhost:4200` (Angular frontend)
- **Browser:** Chromium (configured in `playwright.config.ts`)
- **Timeout:** 30 seconds per test
- **Retries:** 0 locally, 2 on CI
