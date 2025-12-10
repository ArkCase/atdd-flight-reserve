import { Locator, Page } from "@playwright/test";
import { BasePage } from "./basePage";

export class SearchFlightsPage extends BasePage {
    // Navigation
    readonly searchFlightsTab: Locator;

    //Source
    readonly sourceCountryLocator: Locator;
    readonly sourceCityLocator: Locator;
    readonly sourceAirportLocator: Locator;

    //Destination
    readonly destinationCountryLocator: Locator
    readonly destinationCityLocator: Locator;
    readonly destinationAirportLocator: Locator;

    //Search Button
    readonly searchButton: Locator;

    //Available Routes Card
    readonly routesCard: Locator;
    readonly routesCardTitle: Locator;
    readonly routesCardSubtitle: Locator;
    readonly routesCardFromDetail: Locator;
    readonly routesCardToDetail: Locator;
    readonly routesCardStopsDetail: Locator;
    readonly routesCardPrice: Locator;
    readonly routeCards: Locator;
    
    //Error Card
    readonly errorCard: Locator;

    constructor(page: Page) {
            super(page);
    
            // Navigation
            this.searchFlightsTab = page.getByText('Search Flights');

            // Source
            this.sourceCountryLocator = page.locator('mat-form-field:has(mat-label:text("Source Country")) mat-select');
            this.sourceCityLocator = page.getByRole('combobox', { name: 'Source City' });
            this.sourceAirportLocator = page.locator('mat-form-field:has(mat-label:text("Source Airport")) mat-select')

            // Destination
            this.destinationCountryLocator = page.locator('mat-form-field:has(mat-label:text("Destination Country")) mat-select');
            this.destinationCityLocator = page.getByRole('combobox', { name: 'Destination City' });
            this.destinationAirportLocator = page.locator('mat-form-field:has(mat-label:text("Destination Airport")) mat-select');

            // Search Button
            this.searchButton = page.locator('button', { hasText: 'Search' });

            //Available Routes Card
            this.routesCard = page.locator('.routes-card').first();
            this.routesCardTitle = this.routesCard.locator('mat-card-title');
            this.routesCardSubtitle = this.routesCard.locator('mat-card-subtitle');
            this.routesCardFromDetail = this.routesCard.locator('.route-detail').filter({ hasText: 'From:' });
            this.routesCardToDetail = this.routesCard.locator('.route-detail').filter({ hasText: 'To:' });
            this.routesCardStopsDetail = this.routesCard.locator('.route-detail').filter({ hasText: 'Stops:' });
            this.routesCardPrice = this.routesCard.locator('.price');
            this.routeCards = page.locator('.route-card');

            // Error Card
            this.errorCard = page.locator('.error-card').first();
        }
    async searchSource(country: string, city: string, airport: string) {
        // Select country
        await this.sourceCountryLocator.click();
        await this.page.getByRole('option', { name: country }).click();
        
        // Select city
        await this.sourceCityLocator.click();
        await this.sourceCityLocator.fill(city);
        await this.page.getByRole('option', { name: city }).click();
        
        // Select airport
        await this.sourceAirportLocator.click();
        await this.page.getByRole('option', { name: airport }).click();
    }

    async searchDestination(country: string, city: string, airport: string) {
        // Select country
        await this.destinationCountryLocator.click();
        await this.page.getByRole('option', { name: country }).click();
        
        // Select city
        await this.destinationCityLocator.click();
        await this.destinationCityLocator.fill(city);
        await this.page.getByRole('option', { name: city }).click();
        
        // Select airport
        await this.destinationAirportLocator.click();
        await this.page.getByRole('option', { name: airport }).click();
    }
}