import { useState } from "react";
import {
    Dialog,
    DialogTitle,
    DialogContent,
    DialogActions,
    TextField,
    Button,
    MenuItem,
	Box,
	Typography,
	OutlinedInput
} from "@mui/material";
import { createAnimale, updateAnimale } from "../services/AnimaleService";
// 🌟 NOTA: Ho importato 'Specie' (l'oggetto reale) separatamente dai tipi
import { Specie } from "../types";
import type { Animale, SpecieType, Volontario } from "../types";

interface Props {
    open: boolean;
    onClose: () => void;
    onCreated: () => void;
    animaleDaModificare?: Animale | null;
}

export default function AnimaleCreateDialog({ open, onClose, onCreated, animaleDaModificare }: Props) {
    // --- STATI ---
    const [nome, setNome] = useState("");
    const [specie, setSpecie] = useState<SpecieType | "">("");
    const [dataArrivo, setDataArrivo] = useState("");
    const [razza, setRazza] = useState("");
    const [dataNascita, setDataNascita] = useState("");
    const [descrizione, setDescrizione] = useState("");
    const [immagineUrl, setImmagineUrl] = useState("");
    const [volontario, setVolontario] = useState<Volontario | null>(null);

    // --- FUNZIONE RESET (Ora è al posto giusto) ---
    const resetForm = () => {
        setNome("");
        setSpecie("");
        setDataArrivo("");
        setRazza("");
        setDataNascita("");
        setDescrizione("");
        setImmagineUrl("");
        setVolontario(null);
    };

    // --- FUNZIONE SUBMIT (Riceve l'evento del form ed è chiusa correttamente) ---
    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault(); // Evita il refresh della pagina

        const data = {
            nome,
            specie: specie as SpecieType,
            dataArrivo,
            razza: razza || undefined,
            dataNascita: dataNascita || undefined,
            descrizione: descrizione || undefined,
            immagineUrl: immagineUrl || undefined,
            volontario: volontario || undefined,
        };

        try {
            if (animaleDaModificare) {
                await updateAnimale(animaleDaModificare.id, data);
            } else {
                await createAnimale(data);
            }

            resetForm();
            onCreated();
            onClose();
        } catch (error) {
            console.error("Errore salvataggio", error);
        }
    }; // 🌟 Ora la funzione handleSubmit si chiude QUI.

    // --- RETURN DEL COMPONENTE (Ora è nel corpo principale del componente) ---
    return (
        <Dialog open={open} onClose={onClose} fullWidth maxWidth="sm">
            <DialogTitle>
                {animaleDaModificare ? "Modifica Animale" : "Aggiungi Nuovo Animale"}
            </DialogTitle>

            <form onSubmit={handleSubmit}>
                <DialogContent dividers>
                    {/* Campo Nome */}
                    <TextField
                        label="Nome"
                        fullWidth
                        required
                        margin="normal"
                        value={nome}
                        onChange={(e) => setNome(e.target.value)}
                    />

                    {/* Menu a tendina Specie */}
                    <TextField
                        select
                        label="Specie"
                        fullWidth
                        required
                        margin="normal"
                        value={specie}
                        onChange={(e) => setSpecie(e.target.value as SpecieType)}
                    >
                        <MenuItem value="">-- Seleziona --</MenuItem>
                        {Object.keys(Specie).map((chiave) => (
                            <MenuItem key={chiave} value={chiave}>
                                {chiave}
                            </MenuItem>
                        ))}
                    </TextField>

                    <TextField
                        label="Razza"
                        fullWidth
                        margin="dense"
                        value={razza}
                        onChange={(e) => setRazza(e.target.value)}
                    />

					{/* --- CAMPO DATA ARRIVO (SISTEMATO) --- */}
					<Box sx={{ mt: 2, mb: 1 }}>
					    <Typography 
					        variant="body2" 
					        sx={{ mb: 0.5, color: "text.secondary", fontWeight: "medium", display: "block" }}
					    >
					        Data Arrivo *
					    </Typography>
					    <OutlinedInput
					        type="date"
					        fullWidth
					        required
					        value={dataArrivo}
					        onChange={(e) => setDataArrivo(e.target.value)}
					    />
					</Box>

					{/* --- CAMPO DATA NASCITA (SISTEMATO) --- */}
					<Box sx={{ mt: 2, mb: 1 }}>
					    <Typography 
					        variant="body2" 
					        sx={{ mb: 0.5, color: "text.secondary", fontWeight: "medium", display: "block" }}
					    >
					        Data Nascita
					    </Typography>
					    <OutlinedInput
					        type="date"
					        fullWidth
					        value={dataNascita}
					        onChange={(e) => setDataNascita(e.target.value)}
					    />
					</Box>
                    {/* 6. URL Immagine (Opzionale) */}
                    <TextField
                        label="URL Immagine"
                        fullWidth
                        margin="dense"
                        value={immagineUrl}
                        onChange={(e) => setImmagineUrl(e.target.value)}
                    />
                    <TextField
                        label="Descrizione"
                        fullWidth
                        multiline
                        rows={3}
                        margin="dense"
                        value={descrizione}
                        onChange={(e) => setDescrizione(e.target.value)}
                    />
                </DialogContent>

                <DialogActions>
                    <Button onClick={onClose}>Annulla</Button>
                    <Button type="submit" variant="contained" color="primary">
                        Salva
                    </Button>
                </DialogActions>
            </form>
        </Dialog>
    );
} 