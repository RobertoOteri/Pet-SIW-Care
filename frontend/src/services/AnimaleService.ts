import api from './api';
import type {Animale,SpecieType,Volontario} from '../types'; 

export function getAnimali(){
	return api.get<Animale[]>("/rest/animali");
}

export function createAnimale(data:{
	nome: string;
	specie: SpecieType;
	razza?: string;
	dataNascita?: string;
	dataArrivo: string;
	descrizione?: string;
	immagineUrl?: string;
	volontario?: Volontario;
}){
	return api.post<Animale>("/rest/animali", data);
}

export function updateAnimale(id: number, data:{
	nome: string;
	specie: SpecieType;
	razza?: string;
	dataNascita?: string;
	dataArrivo: string;
	descrizione?: string;
	immagineUrl?: string;
	volontario?: Volontario;
}){
	return api.put<Animale>(`/rest/animali/${id}`,data);
}

export function deleteAnimale(id: number){
	return api.delete<void>(`/rest/animali/${id}`);
}