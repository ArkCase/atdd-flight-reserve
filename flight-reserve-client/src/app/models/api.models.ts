// DTOs matching your Java records
export interface CountryDTO {
  id?: number;
  name: string;
}

export interface CityDTO {
  id: number;
  name: string;
  airports: string[];
  country: CountryDTO;
  countryId?: number; // Added for easier airport lookups
}

export interface AirportDTO {
  id: number;
  name: string;
  iata: string;
  icao: string;
  latitude: number;
  longitude: number;
  altitude: number;
  timezone: string;
  dst: string;
  tz: string;
  type: string;
  source: string;
}

export interface RouteDTO {
  id: number;
  codeshare: string;
  stops: number;
  equipment: string;
  price: number;
  srcAirportId: number;
  dstAirportId: number;
  airlineId: number;
}

export interface FlightSearchResult {
  route: RouteDTO;
  sourceAirport: AirportDTO;
  destinationAirport: AirportDTO;
  airline?: string;
}

export interface TaxInfo {
  // Add properties based on what your external API returns
  // For now, using flexible structure
  [key: string]: any;
}

// Request models
export interface CreateCityRequest {
  name: string;
  countryId: number;
  description?: string;
}
