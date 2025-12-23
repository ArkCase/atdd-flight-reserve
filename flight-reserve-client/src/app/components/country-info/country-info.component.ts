import { Component, OnInit  } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatIconModule } from '@angular/material/icon';
import { ApiService } from '../../services/api.service';
import { FormsModule } from '@angular/forms';
import {CountryInfo} from '../../models/api.models';

@Component({
  selector: 'app-country-info',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule,
    MatProgressSpinnerModule,
    MatIconModule,
    FormsModule
  ],
  templateUrl: './country-info.component.html',
  styleUrl: './country-info.component.css'
})
export class CountryInfoComponent implements OnInit{

  countryName = '';
  countries: CountryInfo[] = [];
  error = '';

  constructor(private apiService: ApiService) {}

  ngOnInit(): void {
    this.loadCountryInfo();
  }

  loadCountryInfo() {
    this.error = '';
    this.countries = [];


    if (!this.countryName.trim()) {
     // this.error = 'Please enter a country name';
      return;
    }

    this.apiService.getCountryInfo(this.countryName)
      .subscribe({
      next: (response: CountryInfo[]) => {
        this.countries = response;
      },
        error: () => {
        this.error = 'No countries found or API error';
      }
    });
  }
}
