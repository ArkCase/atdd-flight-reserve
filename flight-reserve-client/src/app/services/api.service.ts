import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import {
  CityDTO,
  AirportDTO,
  RouteDTO,
  TaxInfo,
  CreateCityRequest,
  CountryDTO
} from '../models/api.models';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private baseUrl = '/api';

  constructor(private http: HttpClient) {}

  // City endpoints
  searchCities(countryId: number, query: string, limit: number = 50): Observable<CityDTO[]> {
    const params = new HttpParams()
      .set('countryId', countryId.toString())
      .set('query', query)
      .set('limit', limit.toString());

    return this.http.get<CityDTO[]>(`${this.baseUrl}/cities/search`, { params });
  }

  getCityById(id: number): Observable<CityDTO> {
    return this.http.get<CityDTO>(`${this.baseUrl}/cities/${id}`);
  }

  createCity(city: CreateCityRequest): Observable<CityDTO> {
    return this.http.post<CityDTO>(`${this.baseUrl}/cities`, city);
  }

  deleteCity(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/cities/${id}`);
  }

  // Country endpoints
  getAllCountries(): Observable<CountryDTO[]> {
    return this.http.get<CountryDTO[]>(`${this.baseUrl}/countries`);
  }

  getCountryById(id: number): Observable<CountryDTO> {
    return this.http.get<CountryDTO>(`${this.baseUrl}/countries/${id}`);
  }

  // Airport endpoints
  getAirportsByCityAndCountry(cityId: number, countryId: number): Observable<AirportDTO[]> {
    return this.http.get<AirportDTO[]>(`${this.baseUrl}/airports/by/${cityId}/${countryId}`);
  }

  // Route endpoints
  getRoutesByAirports(srcAirportId: number, dstAirportId: number): Observable<RouteDTO[]> {
    return this.http.get<RouteDTO[]>(`${this.baseUrl}/routes/by/${srcAirportId}/${dstAirportId}`);
  }

  // Flight search
  getCheapestFlight(source: string, destination: string): Observable<any> {
    return this.http.get(`${this.baseUrl}/flights/${source}/${destination}`);
  }

  // Tax information
  getTaxInfo(): Observable<TaxInfo> {
    return this.http.get<TaxInfo>(`${this.baseUrl}/taxes/info`);
  }
}
