import {
  Container,
  TextField,
  Typography,
  Box,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  Button,
  CircularProgress,
  Paper,
} from "@mui/material";
import { useState } from "react";
import "./App.css";
import axios from 'axios';

function App() {
  const [emailContent, setEmailContent] = useState("");
  const [tone, setTone] = useState("");
  const [generatedReply, setGeneratedReply] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const handleSubmit = async () => {
    setLoading(true);
    setError('');
    try {
      const respone = await axios.post("http://localhost:8080/api/email/generate",{
        emailContent
        ,
        tone
      });
      setGeneratedReply(typeof respone.data=='string' ?respone.data : JSON.stringify(respone.data));
      
    } catch (error) {
      setError('Failed to generate Email Reply Please Try again');
      console.error(error);
    }
    finally{
      setLoading(false);
    }
   
          
  };

  return (
    <div className="app-background">
      <Container maxWidth="sm">
        <Paper elevation={10} className="main-card fade-in">
          <Typography variant="h4" className="title">
            ✉️ Email Reply Generator
          </Typography>

          <TextField
            fullWidth
            multiline
            rows={4}
            variant="outlined"
            label="Original Email Content"
            value={emailContent}
            onChange={(e) => setEmailContent(e.target.value)}
            sx={{ mb: 2 }}
            className="input-field"
          />

          <FormControl fullWidth sx={{ mb: 2 }}>
            <InputLabel>Tone (Optional)</InputLabel>
            <Select
              value={tone}
              label="Tone (Optional)"
              onChange={(e) => setTone(e.target.value)}
              className="input-field"
            >
              <MenuItem value="">None</MenuItem>
              <MenuItem value="Professional">Professional</MenuItem>
              <MenuItem value="Casual">Casual</MenuItem>
              <MenuItem value="Friendly">Friendly</MenuItem>
            </Select>
          </FormControl>

          <Button
            variant="contained"
            onClick={handleSubmit}
            disabled={!emailContent || loading}
            fullWidth
            className="generate-btn"
          >
            {loading ? <CircularProgress size={24} /> : "Generate Reply"}
          </Button>

          {generatedReply && (
            <Box className="result-box">
              <Typography variant="h6" gutterBottom>
                Generated Reply:
              </Typography>

              <TextField
                fullWidth
                multiline
                rows={6}
                variant="outlined"
                value={generatedReply}
                inputProps={{ readOnly: true }}
              />

              <Button
                variant="outlined"
                fullWidth
                sx={{ mt: 2 }}
                className="copy-btn"
                onClick={() =>
                  navigator.clipboard.writeText(generatedReply)
                }
              >
                Copy to Clipboard
              </Button>
            </Box>
          )}
        </Paper>
      </Container>
    </div>
  );
}

export default App;
