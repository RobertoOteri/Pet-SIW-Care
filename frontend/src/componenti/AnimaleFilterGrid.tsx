import { useMemo, useState } from "react";
import {
    Box,
    Card,
    CardContent,
    CardMedia,
    Chip,
    FormControl,
    InputLabel,
    MenuItem,
    Select,
    Slider,
    Typography,
    Grid,
    IconButton,
} from "@mui/material";
import type { Animale } from "../types";

type SortOption = "nome-asc" | "nome-desc" | "eta-asc" | "eta-desc";

interface Props {
    animali: Animale[];
    deleteAnimale: (id: number) => void;
    modificaAnimale: (animale: Animale) => void;
}

export default function AnimaleFilterGrid({ animali, deleteAnimale, modificaAnimale }: Props) {
    // Stati per i filtri
    const [selectedSpecie, setSelectedSpecie] = useState<string | null>(null);
    const [etaRange, setEtaRange] = useState<[number, number] | null>(null);
    const [sort, setSort] = useState<SortOption>("nome-asc");

    // Funzione helper per calcolare l'età dall'anno di nascita
    const calcolaEta = (dataNascita: string): number => {
        if (!dataNascita) return 0;
        const annoNascita = new Date(dataNascita).getFullYear();
        const annoCorrente = new Date().getFullYear();
        return annoCorrente - annoNascita;
    };

    // Estraiamo le specie uniche (CANE, GATTO, ecc.) per i Chip
    const specieElenco = useMemo(() => {
        const set = new Set<string>();
        animali.forEach((a) => {
            if (a.specie) set.add(a.specie);
        });
        return Array.from(set).sort();
    }, [animali]);

    // Calcoliamo l'età minima e massima in base agli animali presenti
    const [minEta, maxEta] = useMemo(() => {
        if (animali.length === 0) return [0, 20];
        const etaElenco = animali.map((a) => calcolaEta(a.dataNascita));
        return [Math.min(...etaElenco), Math.max(...etaElenco)];
    }, [animali]);

    const effectiveRange = useMemo(
        () => etaRange ?? [minEta, maxEta],
        [etaRange, minEta, maxEta]
    );

    // Logica di filtraggio e ordinamento
    const filtered = useMemo(() => {
        let result = animali;

        // 1. Filtro per Specie
        if (selectedSpecie) {
            result = result.filter((a) => a.specie === selectedSpecie);
        }

        // 2. Filtro per Età
        result = result.filter((a) => {
            const eta = calcolaEta(a.dataNascita);
            return eta >= effectiveRange[0] && eta <= effectiveRange[1];
        });

        // 3. Ordinamento dinamico
        result = [...result].sort((a, b) => {
            switch (sort) {
                case "nome-asc":
                    return a.nome.localeCompare(b.nome);
                case "nome-desc":
                    return b.nome.localeCompare(a.nome);
                case "eta-asc":
                    return calcolaEta(a.dataNascita) - calcolaEta(b.dataNascita);
                case "eta-desc":
                    return calcolaEta(b.dataNascita) - calcolaEta(a.dataNascita);
            }
        });

        return result;
    }, [animali, selectedSpecie, effectiveRange, sort]);

    const civileUrl = (url: string) => {
        // Se l'URL inizia già con http (es: un link internet), usalo così com'è
        if (url.startsWith("http")) return url;

        // Altrimenti, forza il browser a prenderlo dal backend Spring Boot
        return `http://localhost:8080${url}`;
    };

    return (
        <Box sx={{ p: 3 }}>
            {/* Sezione Filtri */}
            <Box sx={{ mb: 4, display: "flex", flexDirection: "column", gap: 2 }}>

                {/* Chip per filtrare la Specie */}
                <Box sx={{ display: "flex", flexWrap: "wrap", gap: 1 }}>
                    <Chip
                        label="Tutte le specie"
                        variant={selectedSpecie === null ? "filled" : "outlined"}
                        onClick={() => setSelectedSpecie(null)}
                        color="primary"
                    />
                    {specieElenco.map((specie) => (
                        <Chip
                            key={specie}
                            label={specie}
                            variant={selectedSpecie === specie ? "filled" : "outlined"}
                            onClick={() => setSelectedSpecie(specie)}
                            color="primary"
                        />
                    ))}
                </Box>

                {/* Slider Età + Select Ordinamento */}
                <Box sx={{ display: "flex", gap: 4, alignItems: "center", flexWrap: "wrap" }}>
                    <Box sx={{ minWidth: 250, flex: 1 }}>
                        <Typography variant="body2" gutterBottom sx={{ fontWeight: "bold" }}>
                            Intervallo Età: {effectiveRange[0]} anni — {effectiveRange[1]} anni
                        </Typography>
                        <Slider
                            value={effectiveRange}
                            onChange={(_, v) => setEtaRange(v as [number, number])}
                            min={minEta}
                            max={maxEta}
                            step={1}
                            valueLabelDisplay="auto"
                        />
                    </Box>

                    <FormControl size="small" sx={{ minWidth: 200 }}>
                        <InputLabel>Ordinamento</InputLabel>
                        <Select
                            value={sort}
                            label="Ordinamento"
                            onChange={(e) => setSort(e.target.value as SortOption)}
                        >
                            <MenuItem value="nome-asc">Nome A→Z</MenuItem>
                            <MenuItem value="nome-desc">Nome Z→A</MenuItem>
                            <MenuItem value="eta-asc">Età crescente</MenuItem>
                            <MenuItem value="eta-desc">Età decrescente</MenuItem>
                        </Select>
                    </FormControl>
                </Box>
            </Box>

            {/* Griglia degli Animali */}
            <Grid container spacing={3}>
                {filtered.map((animale) => (
                    <Grid size={{ xs: 12, sm: 6, md: 4 }} key={animale.id}>
                        <Card variant="outlined" sx={{ position: "relative", height: "100%", display: "flex", flexDirection: "column", '&:hover': { boxShadow: 3 } }}>

                            {/* Pulsante Modifica */}
                            <IconButton
                                size="small"
                                onClick={() => modificaAnimale(animale)}
                                sx={{
                                    position: "absolute",
                                    top: 48,
                                    right: 8,
                                    zIndex: 2,
                                    backgroundColor: "rgba(255, 255, 255, 0.8)",
                                    color: "#0288d1",
                                    '&:hover': { backgroundColor: "#e1f5fe" }
                                }}
                            >
                                ✏️
                            </IconButton>

                            {/* Pulsante Elimina */}
                            <IconButton
                                size="small"
                                onClick={() => {
                                    if (window.confirm(`Sei sicuro di voler eliminare l'animale ${animale.nome}?`)) {
                                        deleteAnimale(animale.id);
                                    }
                                }}
                                sx={{
                                    position: "absolute",
                                    top: 8,
                                    right: 8,
                                    zIndex: 2,
                                    backgroundColor: "rgba(255, 255, 255, 0.8)",
                                    color: "#ef4444",
                                    '&:hover': { backgroundColor: "#fee2e2" }
                                }}
                            >
                                ❌
                            </IconButton>

                            {/* Immagine Animale */}
                            <CardMedia
                                component="img"
                                height="180"
                                image={
                                    animale.immagineUrl
                                        ? civileUrl(animale.immagineUrl)
                                        : "https://via.placeholder.com/180"
                                }
                                alt={animale.nome}
                                sx={{ objectFit: "cover" }}
                            />

                            <CardContent sx={{ flexGrow: 1 }}>
                                <Box sx={{ display: "flex", justifyContent: "space-between", alignItems: "baseline", mb: 1 }}>
                                    <Typography variant="h5" sx={{ fontWeight: "bold" }}>
                                        🐾 {animale.nome}
                                    </Typography>
                                    <Chip label={animale.specie} size="small" color="secondary" />
                                </Box>

                                <Typography variant="body2" color="text.secondary">
                                    <strong>Razza:</strong> {animale.razza}
                                </Typography>
                                <Typography variant="body2" color="text.secondary">
                                    <strong>Età:</strong> {calcolaEta(animale.dataNascita)} anni
                                </Typography>
                                <Typography variant="body2" color="text.secondary" sx={{ mt: 1, minHeight: "40px" }}>
                                    {animale.descrizione}
                                </Typography>

                                {/* Sezione Volontario */}
                                {animale.volontario && (
                                    <Box sx={{ mt: 2, pt: 2, borderTop: "1px solid #e0e0e0" }}>
                                        <Typography variant="caption" display="block" color="text.secondary">
                                            🙋‍♂️ <strong>Volontario:</strong> {animale.volontario.nome} {animale.volontario.cognome}
                                        </Typography>
                                    </Box>
                                )}
                            </CardContent>
                        </Card>
                    </Grid>
                ))}
            </Grid>
        </Box>
    );
}