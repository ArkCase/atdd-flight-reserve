import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, FormControl } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatDividerModule } from '@angular/material/divider';
import { MatTooltipModule } from '@angular/material/tooltip';
import { ApiService } from '../../services/api.service';
import { CityDTO, CountryDTO, CreateCityRequest } from '../../models/api.models';
import { Observable, of } from 'rxjs';
import { debounceTime, distinctUntilChanged, switchMap, catchError, startWith } from 'rxjs/operators';

@Component({
  selector: 'app-city-management',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatAutocompleteModule,
    MatButtonModule,
    MatCardModule,
    MatTableModule,
    MatIconModule,
    MatProgressSpinnerModule,
    MatSnackBarModule,
    MatDialogModule,
    MatDividerModule,
    MatTooltipModule
  ],
  templateUrl: './city-management.component.html',
  styleUrl: './city-management.component.css'
})
export class CityManagementComponent implements OnInit {
  cities: CityDTO[] = [];
  displayedColumns: string[] = ['id', 'name', 'country', 'airports', 'actions'];
  
  // Form for adding new city
  cityNameControl = new FormControl('');
  countryControl = new FormControl('');
  descriptionControl = new FormControl('');
  
  // Autocomplete for countries (add form)
  filteredCountries$: Observable<CountryDTO[]> = of([]);
  selectedCountry: CountryDTO | null = null;
  
  // Search for cities table
  searchCountryControl = new FormControl('');
  searchCityControl = new FormControl('');
  filteredSearchCountries$: Observable<CountryDTO[]> = of([]);
  selectedSearchCountry: CountryDTO | null = null;
  
  loading = false;
  addingCity = false;
  searchingCities = false;
  error: string | null = null;
  hasSearched = false;

  constructor(
    private apiService: ApiService,
    private snackBar: MatSnackBar,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.setupCountryAutocomplete();
    this.setupSearchCountryAutocomplete();
    this.setupCitySearch();
  }

  // ===== ADD CITY FORM =====
  
  setupCountryAutocomplete(): void {
    this.filteredCountries$ = this.countryControl.valueChanges.pipe(
      startWith(''),
      debounceTime(300),
      distinctUntilChanged(),
      switchMap(value => {
        const searchQuery = typeof value === 'string' ? value : '';
        
        return this.apiService.getAllCountries().pipe(
          switchMap(countries => {
            if (!searchQuery) {
              return of(countries);
            }
            const filtered = countries.filter(country =>
              country.name.toLowerCase().includes(searchQuery.toLowerCase())
            );
            return of(filtered);
          }),
          catchError(() => of([]))
        );
      })
    );
  }

  displayCountry(country: CountryDTO | null): string {
    return country ? country.name : '';
  }

  onCountrySelected(country: CountryDTO): void {
    this.selectedCountry = country;
  }

  addCity(): void {
    const cityName = this.cityNameControl.value?.trim();
    const description = this.descriptionControl.value?.trim();
    
    if (!cityName) {
      this.showSnackBar('Please enter a city name', 'error');
      return;
    }
    
    if (!this.selectedCountry) {
      this.showSnackBar('Please select a country', 'error');
      return;
    }

    this.addingCity = true;
    this.error = null;

    const newCity: CreateCityRequest = {
      name: cityName,
      countryId: this.selectedCountry.id!,
      description: description || ''
    };

    this.apiService.createCity(newCity).subscribe({
      next: (city) => {
        // If we're currently searching, add to the list if it matches
        if (this.hasSearched && this.selectedSearchCountry?.id === city.country.id) {
          this.cities.push(city);
        }
        this.showSnackBar(`City "${city.name}" added successfully!`, 'success');
        this.resetForm();
        this.addingCity = false;
      },
      error: (err) => {
        this.error = 'Failed to add city';
        this.addingCity = false;
        console.error(err);
        this.showSnackBar('Failed to add city', 'error');
      }
    });
  }

  resetForm(): void {
    this.cityNameControl.setValue('');
    this.countryControl.setValue('');
    this.descriptionControl.setValue('');
    this.selectedCountry = null;
  }

  // ===== SEARCH CITIES TABLE =====

  setupSearchCountryAutocomplete(): void {
    this.filteredSearchCountries$ = this.searchCountryControl.valueChanges.pipe(
      startWith(''),
      debounceTime(300),
      distinctUntilChanged(),
      switchMap(value => {
        const searchQuery = typeof value === 'string' ? value : '';
        
        return this.apiService.getAllCountries().pipe(
          switchMap(countries => {
            if (!searchQuery) {
              return of(countries);
            }
            const filtered = countries.filter(country =>
              country.name.toLowerCase().includes(searchQuery.toLowerCase())
            );
            return of(filtered);
          }),
          catchError(() => of([]))
        );
      })
    );
  }

  onSearchCountrySelected(country: CountryDTO): void {
    this.selectedSearchCountry = country;
    // Auto-trigger search if city field has text
    const cityQuery = this.searchCityControl.value;
    if (cityQuery && cityQuery.trim().length >= 2) {
      this.searchCities();
    }
  }

  setupCitySearch(): void {
    this.searchCityControl.valueChanges.pipe(
      debounceTime(500),
      distinctUntilChanged()
    ).subscribe(value => {
      // Auto-search if we have country selected and at least 2 characters
      if (this.selectedSearchCountry && value && value.trim().length >= 2) {
        this.searchCities();
      }
    });
  }

  searchCities(): void {
    const cityQuery = this.searchCityControl.value?.trim();
    
    if (!this.selectedSearchCountry) {
      this.showSnackBar('Please select a country first', 'error');
      return;
    }

    if (!cityQuery || cityQuery.length < 2) {
      this.showSnackBar('Please enter at least 2 characters to search', 'error');
      return;
    }

    this.searchingCities = true;
    this.error = null;
    this.hasSearched = true;

    this.apiService.searchCities(this.selectedSearchCountry.id!, cityQuery, 100).subscribe({
      next: (cities) => {
        this.cities = cities;
        this.searchingCities = false;
        if (cities.length === 0) {
          this.showSnackBar('No cities found', 'info');
        }
      },
      error: (err) => {
        this.error = 'Failed to search cities';
        this.searchingCities = false;
        console.error(err);
        this.showSnackBar('Failed to search cities', 'error');
      }
    });
  }

  clearSearch(): void {
    this.searchCountryControl.setValue('');
    this.searchCityControl.setValue('');
    this.selectedSearchCountry = null;
    this.cities = [];
    this.hasSearched = false;
    this.error = null;
  }

  // ===== DELETE CITY =====

  deleteCity(city: CityDTO): void {
    if (!confirm(`Are you sure you want to delete ${city.name}?`)) {
      return;
    }

    this.loading = true;
    this.error = null;

    this.apiService.deleteCity(city.id).subscribe({
      next: () => {
        this.cities = this.cities.filter(c => c.id !== city.id);
        this.loading = false;
        this.showSnackBar(`City "${city.name}" deleted successfully!`, 'success');
      },
      error: (err) => {
        this.error = `Failed to delete ${city.name}`;
        this.loading = false;
        console.error(err);
        this.showSnackBar(`Failed to delete ${city.name}`, 'error');
      }
    });
  }

  // ===== UTILITIES =====

  getAirportsDisplay(airports: string[]): string {
    if (!airports || airports.length === 0) {
      return 'No airports';
    }
    return airports.join(', ');
  }

  showSnackBar(message: string, type: 'success' | 'error' | 'info'): void {
    const panelClass = type === 'success' ? 'snackbar-success' : 
                       type === 'error' ? 'snackbar-error' : 
                       'snackbar-info';
    
    this.snackBar.open(message, 'Close', {
      duration: 3000,
      horizontalPosition: 'end',
      verticalPosition: 'top',
      panelClass: panelClass
    });
  }
}
