import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatTabsModule } from '@angular/material/tabs';
import { MatIconModule } from '@angular/material/icon';
import { FlightSearchComponent } from './components/flight-search/flight-search.component';
import { CityManagementComponent } from './components/city-management/city-management.component';
import { TaxInfoComponent } from './components/tax-info/tax-info.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule,
    MatToolbarModule,
    MatTabsModule,
    MatIconModule,
    FlightSearchComponent,
    CityManagementComponent,
    TaxInfoComponent
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'Flight Reservation System';
}
