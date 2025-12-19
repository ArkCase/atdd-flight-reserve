import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, FormControl } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';
import { ApiService } from '../../services/api.service';
import { CityDTO, AirportDTO, RouteDTO, CountryDTO } from '../../models/api.models';
import { Observable, of } from 'rxjs';
import { debounceTime, distinctUntilChanged, switchMap, catchError, startWith } from 'rxjs/operators';

@Component({
  selector: 'app-flight-search',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatSelectModule,
    MatAutocompleteModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
    MatProgressSpinnerModule,
    MatIconModule,
    MatDividerModule
  ],
  templateUrl: './flight-search.component.html',
  styleUrl: './flight-search.component.css'
})
export class FlightSearchComponent implements OnInit {
  countries: CountryDTO[] = [];
  
  // Source selection
  sourceCountry: CountryDTO | null = null;
  sourceCityControl = new FormControl('');
  filteredSourceCities$: Observable<CityDTO[]> = of([]);
  selectedSourceCity: CityDTO | null = null;
  sourceAirports: AirportDTO[] = [];
  selectedSourceAirport: AirportDTO | null = null;
  
  // Destination selection
  destinationCountry: CountryDTO | null = null;
  destinationCityControl = new FormControl('');
  filteredDestinationCities$: Observable<CityDTO[]> = of([]);
  selectedDestinationCity: CityDTO | null = null;
  destinationAirports: AirportDTO[] = [];
  selectedDestinationAirport: AirportDTO | null = null;
  
  // Routes
  routes: RouteDTO[] = [];
  
  loading = false;
  error: string | null = null;

  constructor(private apiService: ApiService) {}

  ngOnInit(): void {
    this.loadCountries();
  }

  loadCountries(): void {
    this.loading = true;
    this.apiService.getAllCountries().subscribe({
      next: (countries) => {
        this.countries = countries;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Failed to load countries';
        this.loading = false;
        console.error(err);
      }
    });
  }

  onSourceCountryChange(): void {
    this.sourceCityControl.setValue('');
    this.selectedSourceCity = null;
    this.sourceAirports = [];
    this.selectedSourceAirport = null;
    
    if (this.sourceCountry) {
      this.setupSourceCityAutocomplete();
    }
  }

  onDestinationCountryChange(): void {
    this.destinationCityControl.setValue('');
    this.selectedDestinationCity = null;
    this.destinationAirports = [];
    this.selectedDestinationAirport = null;
    
    if (this.destinationCountry) {
      this.setupDestinationCityAutocomplete();
    }
  }

  setupSourceCityAutocomplete(): void {
    if (!this.sourceCountry) return;

    this.filteredSourceCities$ = this.sourceCityControl.valueChanges.pipe(
      startWith(''),
      debounceTime(300),
      distinctUntilChanged(),
      switchMap(value => {
        const searchQuery = typeof value === 'string' ? value : '';
        
        if (!searchQuery || searchQuery.length < 2) {
          return of([]);
        }
        
        return this.apiService.searchCities(this.sourceCountry!.id!, searchQuery).pipe(
          catchError(err => {
            console.error('Error searching cities:', err);
            return of([]);
          })
        );
      })
    );
  }

  setupDestinationCityAutocomplete(): void {
    if (!this.destinationCountry) return;

    this.filteredDestinationCities$ = this.destinationCityControl.valueChanges.pipe(
      startWith(''),
      debounceTime(300),
      distinctUntilChanged(),
      switchMap(value => {
        const searchQuery = typeof value === 'string' ? value : '';
        
        if (!searchQuery || searchQuery.length < 2) {
          return of([]);
        }
        
        return this.apiService.searchCities(this.destinationCountry!.id!, searchQuery).pipe(
          catchError(err => {
            console.error('Error searching cities:', err);
            return of([]);
          })
        );
      })
    );
  }

  onSourceCitySelected(city: CityDTO): void {
    this.selectedSourceCity = city;
    this.loadSourceAirports();
  }

  onDestinationCitySelected(city: CityDTO): void {
    this.selectedDestinationCity = city;
    this.loadDestinationAirports();
  }

  displayCity(city: CityDTO | null): string {
    return city ? `${city.name}, ${city.country.name}` : '';
  }

  loadSourceAirports(): void {
    if (!this.selectedSourceCity || !this.selectedSourceCity.country.id) {
      return;
    }
    
    this.loading = true;
    this.apiService.getAirportsByCityAndCountry(
      this.selectedSourceCity.id, 
      this.selectedSourceCity.country.id
    ).subscribe({
      next: (airports) => {
        this.sourceAirports = airports;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Failed to load source airports';
        this.loading = false;
        console.error(err);
      }
    });
  }

  loadDestinationAirports(): void {
    if (!this.selectedDestinationCity || !this.selectedDestinationCity.country.id) {
      return;
    }
    
    this.loading = true;
    this.apiService.getAirportsByCityAndCountry(
      this.selectedDestinationCity.id, 
      this.selectedDestinationCity.country.id
    ).subscribe({
      next: (airports) => {
        this.destinationAirports = airports;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Failed to load destination airports';
        this.loading = false;
        console.error(err);
      }
    });
  }

  searchRoutes(): void {
    if (!this.selectedSourceAirport || !this.selectedDestinationAirport) {
      this.error = 'Please select both source and destination airports';
      return;
    }

    this.loading = true;
    this.error = null;
    this.routes = [];
    
    this.apiService.getRoutesByAirports(
      this.selectedSourceAirport.id,
      this.selectedDestinationAirport.id
    ).subscribe({
      next: (routes) => {
        this.routes = routes;
        this.loading = false;
        if (routes.length === 0) {
          this.error = 'No routes found between these airports';
        }
      },
      error: (err) => {
        this.error = 'Failed to search routes';
        this.loading = false;
        console.error(err);
      }
    });
  }

  resetSearch(): void {
    this.sourceCountry = null;
    this.sourceCityControl.setValue('');
    this.selectedSourceCity = null;
    this.sourceAirports = [];
    this.selectedSourceAirport = null;
    
    this.destinationCountry = null;
    this.destinationCityControl.setValue('');
    this.selectedDestinationCity = null;
    this.destinationAirports = [];
    this.selectedDestinationAirport = null;
    
    this.routes = [];
    this.error = null;
  }
}
