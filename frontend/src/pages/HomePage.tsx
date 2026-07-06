import { useEffect, useState } from "react";
import { Box, Button, Container, Typography } from "@mui/material";
import AddIcon from "@mui/icons-material/Add";
import AnimaleCreateDialog from "../componenti/AnimaleCreateDialog";
import AnimaleFilterGrid from "../componenti/AnimaleFilterGrid"; 
import type { Animale } from "../types";
import { getAnimali, deleteAnimale } from "../services/AnimaleService";

export default function HomePage() {
  const [animali, setAnimali] = useState<Animale[]>([]);
  const [dialogOpen, setDialogOpen] = useState(false);
  const [animaleModifica, setAnimaleModifica] = useState<Animale | null>(null);

  // Carica gli animali dal backend Spring Boot
  const loadAnimali = () => {
    getAnimali()
      .then((res) => {
        // Controlliamo se res.data esiste ed è effettivamente un Array
        if (res && Array.isArray(res.data)) {
          setAnimali(res.data);
        } else {
          console.warn("I dati ricevuti non sono un array:", res.data);
          setAnimali([]);
        }
      })
      .catch((err) => {
        console.error("Errore nel recupero degli animali:", err);
        setAnimali([]); // Evitiamo il crash passando un array vuoto
      });
  };
  
  // Funzione per eliminare un animale tramite ID
  const eliminaAnimale = (id: number) => {
    deleteAnimale(id)
      .then(() => {
        loadAnimali(); // Rinfresca la lista dopo l'eliminazione
      })
      .catch((err) => {
        console.error("Errore durante l'eliminazione dell'animale", err);
      });
  };
  
  // Funzione per aprire il popup in modalità Modifica
  const modificaAnimale = (animale: Animale) => {
    setAnimaleModifica(animale);
    setDialogOpen(true);
  };

  // Funzione per aprire il popup in modalità Creazione (Nuovo)
  const handleOpenCreate = () => {
    setAnimaleModifica(null);
    setDialogOpen(true);
  };
  
  // Funzione per chiudere il popup e resettare l'animale selezionato
  const handleCloseDialog = () => {
    setDialogOpen(false);
    setAnimaleModifica(null);
  };

  // Carica la lista al primo avvio della pagina
  useEffect(() => {
    loadAnimali();
  }, []);

  return (
    <Container maxWidth="lg" sx={{ py: 4 }}>
      {/* Header della pagina */}
      <Box sx={{ display: "flex", justifyContent: "space-between", alignItems: "center", mb: 3 }}>
        <Typography variant="h4">🐾 Gestione Rifugio Animali</Typography>
        <Button 
          variant="contained" 
          startIcon={<AddIcon />} 
          onClick={handleOpenCreate}
          color="primary"
        >
          Nuovo Animale
        </Button>
      </Box>

      {/* Griglia/Tabella che mostra gli animali con i pulsanti Modifica ed Elimina */}
      <AnimaleFilterGrid 
        animali={animali} 
        deleteAnimale={eliminaAnimale} 
        modificaAnimale={modificaAnimale}
      />

      {/* Popup di Creazione e Modifica */}
      <AnimaleCreateDialog
        open={dialogOpen}
        onClose={handleCloseDialog}
        onCreated={loadAnimali} // Quando il form salva con successo, riesegue loadAnimali
        animaleDaModificare={animaleModifica}
      />
    </Container>
  );
}