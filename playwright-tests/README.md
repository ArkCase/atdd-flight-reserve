# Playwright TypeScript Tests

This directory contains the **primary test suite** for the Flight Reservation System, written in TypeScript using Playwright.

## 🚀 Quick Start

### Prerequisites

- Node.js 18+ installed
- Docker and Docker Compose (to run the application)

### Installation

```bash
cd playwright-tests
npm install
npx playwright install
```

### Running Tests

**Start the application first:**

```bash
# In the system-test directory
cd ../system-test
docker compose up -d
```

**Run all tests:**

```bash
npm test
```

**Run UI tests:**

```bash
npm run test:ui
```

**Run tests in headed mode (see browser):**

```bash
npm run test:headed
```

**Debug tests:**

```bash
npm run test:debug
```

**View test report:**

```bash
npm run test:report
```

## 📁 Project Structure

```
playwright-tests/
├── tests/
│   └── ui/                 # UI tests
├── pages/                  # Page Objects
├── fixtures/               # Test data
├── playwright.config.ts    # Playwright configuration
├── package.json
└── README.md
```

### UI Tests (`tests/ui/`)
End-to-end UI tests for the Flight Reservation System using the Page Object Model.

## 🛠️ Writing New Tests

### Using Page Objects

```typescript
import { CityManagementPage } from '../../pages/cityManagementPage';

test('should create a city', async ({ page }) => {
  const cityPage = new CityManagementPage(page);
  await cityPage.navigateToCityManagement();
  await cityPage.addCity('London', 'UK', 'Test');
});
```

### Using Test Data

```typescript
import { Endpoints, TestCities, SampleCity } from '../../fixtures/test-data';

test('example test', async ({ request }) => {
  // Use predefined endpoints
  const response = await request.get(Endpoints.cities.byName('Skopje'));
  
  // Use sample data
  const newCity = SampleCity;
});
```

## 📊 Test Reports

After running tests, reports are generated in:
- **HTML Report**: `playwright-report/index.html`
- **JSON Report**: `test-results/results.json`
- **JUnit XML**: `test-results/junit.xml` (for CI/CD)

View the HTML report:
```bash
npm run test:report
```

## 🔧 Configuration

Edit `playwright.config.ts` to customize:
- Base URL
- Timeout settings
- Browser configurations
- Reporter options
- Retry logic

**Environment Variables:**
- `BASE_URL`: Override the base URL (default: `http://localhost:8080`)
- `CI`: Set to `true` in CI/CD environments

## 🐛 Debugging

**Debug a specific test:**
```bash
npx playwright test tests/api/cities-api.spec.ts --debug
```

**Generate test code:**
```bash
npm run test:codegen
```

**View trace files:**
```bash
npx playwright show-trace test-results/trace.zip
```

## 📝 Best Practices

1. **Use descriptive test names** - Test names should clearly describe what is being tested
2. **Follow AAA pattern** - Arrange, Act, Assert
3. **Use helpers** - Reuse API helpers and test data
4. **Validate schemas** - Don't just check status codes, validate response structure
5. **Clean up test data** - Use unique identifiers for test data
6. **Keep tests independent** - Tests should not depend on each other

## 🔄 CI/CD Integration

Tests are automatically run in the CI/CD pipeline via GitHub Actions. See `.github/workflows/acceptance-stage.yml`.

## 📚 Resources

- [Playwright Documentation](https://playwright.dev)
- [Playwright API Testing](https://playwright.dev/docs/api-testing)
- [TypeScript Documentation](https://www.typescriptlang.org/docs/)

## ❓ Troubleshooting

**Tests fail with connection errors:**
- Ensure the application is running: `docker compose up -d` in `system-test/`
- Check if port 8080 is accessible: `curl http://localhost:8080/api/echo`

**Playwright not found:**
```bash
npx playwright install
```

**TypeScript errors:**
```bash
npm install
```

## 📧 Support

For questions or issues, please contact the development team or create an issue in the repository.
