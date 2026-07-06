export const Specie={
	CANE:'CANE',
	GATTO: 'GATTO',
	CRICETO: 'CRICETO',
	VOLATILE: 'VOLATILE',
	RETTILE: 'RETTILE',
	EQUINO: 'EQUINO',
	ALTRO: 'ALTRO'
}as const;

export type SpecieType = typeof Specie[keyof typeof Specie];

export interface Volontario{
	id:number;
	nome: string;
	cognome:string;
	codiceFiscale: string;
	specializzazione: string;
	dataDiNascita?: string;
	immagineUrl?: string;
}


export interface Animale{
	id: number;
	nome: string;
	specie: SpecieType;
	razza?: string;
	dataNascita?: string;
	dataArrivo: string;
	descrizione?: string;
	immagineUrl?: string;
	volontario?: Volontario;
}