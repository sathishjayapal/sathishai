
-- First, install the pgvector extension
CREATE EXTENSION IF NOT EXISTS vector;

-- Verify the extension is installed
SELECT * FROM pg_extension WHERE extname = 'vector';

-- Drop the existing vector table (this will lose existing data)
DROP TABLE IF EXISTS vector_store;

-- Recreate with correct dimensions for your embedding model
CREATE TABLE vector_store (
                              id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                              content TEXT,
                              metadata JSONB,
                              embedding VECTOR(768)  -- Match your embedding model's output
);

-- Create index for better performance
CREATE INDEX ON vector_store USING ivfflat (embedding vector_cosine_ops) WITH (lists = 100);