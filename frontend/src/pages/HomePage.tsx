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

  
  const loadAnimali = () => {
    getAnimali()
      .then((res) => {
        if (res && Array.isArray(res.data)) {
          setAnimali(res.data);
        } else {
          console.warn("I dati ricevuti non sono un array:", res.data);
          setAnimali([]);
        }
      })
      .catch((err) => {
        console.error("Errore nel recupero degli animali:", err);
        setAnimali([]); 
      });
  };
  

  const eliminaAnimale = (id: number) => {
    deleteAnimale(id)
      .then(() => {
        loadAnimali(); 
      })
      .catch((err) => {
        console.error("Errore durante l'eliminazione dell'animale", err);
      });
  };
  

  const modificaAnimale = (animale: Animale) => {
    setAnimaleModifica(animale);
    setDialogOpen(true);
  };


  const handleOpenCreate = () => {
    setAnimaleModifica(null);
    setDialogOpen(true);
  };
  

  const handleCloseDialog = () => {
    setDialogOpen(false);
    setAnimaleModifica(null);
  };

 
  useEffect(() => {
    loadAnimali();
  }, []);

  return (
    <Container maxWidth="lg" sx={{ py: 4 }}>

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

      <AnimaleFilterGrid 
        animali={animali} 
        deleteAnimale={eliminaAnimale} 
        modificaAnimale={modificaAnimale}
      />

      <AnimaleCreateDialog
        open={dialogOpen}
        onClose={handleCloseDialog}
        onCreated={loadAnimali} 
        animaleDaModificare={animaleModifica}
      />
    </Container>
  );
}