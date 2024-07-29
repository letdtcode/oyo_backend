import { useState } from 'react';
import { TextField, Button, Typography, Box } from '@mui/material';
import { t } from 'i18next';
import FramePage from '~/components/FramePage/FramePage';
export default function ContactForm() {
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [message, setMessage] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();
        //
    };

    return (
        <FramePage>
            <Box
                sx={{
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'center',
                    height: '100vh'
                }}
            >
                <Box sx={{ maxWidth: 600, mx: 'auto', p: 2 }}>
                    <Typography variant="h4" align="center" mb={2}>
                        {t('common.contactUs')}
                    </Typography>
                    <form onSubmit={handleSubmit}>
                        <TextField
                            fullWidth
                            label={t('label.fullname')}
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                            margin="normal"
                            required
                        />
                        <TextField
                            fullWidth
                            label="Email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            margin="normal"
                            required
                            type="email"
                        />
                        <TextField
                            fullWidth
                            label={t('label.content')}
                            value={message}
                            onChange={(e) => setMessage(e.target.value)}
                            margin="normal"
                            required
                            multiline
                            rows={4}
                        />
                        <Button variant="contained" type="submit" sx={{ mt: 2 }}>
                            {t('common.send')}
                        </Button>
                    </form>
                </Box>
            </Box>
        </FramePage>
    );
}
