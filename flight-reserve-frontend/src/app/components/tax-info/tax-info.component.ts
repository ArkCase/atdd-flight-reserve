import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatIconModule } from '@angular/material/icon';
import { ApiService } from '../../services/api.service';
import { TaxInfo } from '../../models/api.models';

@Component({
  selector: 'app-tax-info',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule,
    MatProgressSpinnerModule,
    MatIconModule
  ],
  templateUrl: './tax-info.component.html',
  styleUrl: './tax-info.component.css'
})
export class TaxInfoComponent implements OnInit {
  taxInfo: TaxInfo | null = null;
  loading = false;
  error: string | null = null;

  constructor(private apiService: ApiService) {}

  ngOnInit(): void {
    this.loadTaxInfo();
  }

  loadTaxInfo(): void {
    this.loading = true;
    this.error = null;

    this.apiService.getTaxInfo().subscribe({
      next: (info) => {
        this.taxInfo = info;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Failed to load tax information';
        this.loading = false;
        console.error(err);
      }
    });
  }

  refresh(): void {
    this.loadTaxInfo();
  }

  getObjectKeys(obj: any): string[] {
    return obj ? Object.keys(obj) : [];
  }

  formatValue(value: any): string {
    if (typeof value === 'number') {
      return value.toLocaleString();
    }
    if (typeof value === 'boolean') {
      return value ? 'Yes' : 'No';
    }
    if (value === null || value === undefined) {
      return 'N/A';
    }
    return String(value);
  }

  formatKey(key: string): string {
    return key
      .replace(/([A-Z])/g, ' $1')
      .replace(/^./, str => str.toUpperCase())
      .trim();
  }
}
