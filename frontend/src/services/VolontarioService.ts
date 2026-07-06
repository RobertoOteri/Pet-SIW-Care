import api from './api';
import type {Volontario} from '../types'; 

export function getVolontari(){
	return api.get<Volontario[]>("/rest/volontari");
}