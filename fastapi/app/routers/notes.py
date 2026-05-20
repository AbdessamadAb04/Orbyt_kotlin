from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session
from typing import List
from app import schemas, models
from app.database import SessionLocal

router = APIRouter(
    prefix="/notes",
    tags=["notes"],
)

def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()

@router.post("/", response_model=schemas.Note)
def create_note(note: schemas.NoteCreate, db: Session = Depends(get_db)):
    db_note = models.Note(**note.dict())
    db.add(db_note)
    db.commit()
    db.refresh(db_note)
    return db_note

@router.get("/", response_model=List[schemas.Note])
def read_notes(skip: int = 0, limit: int = 100, db: Session = Depends(get_db)):
    notes = db.query(models.Note).offset(skip).limit(limit).all()
    return notes

@router.post("/folders", response_model=schemas.NoteFolder)
def create_note_folder(note_folder: schemas.NoteFolderCreate, db: Session = Depends(get_db)):
    db_note_folder = models.NoteFolder(**note_folder.dict())
    db.add(db_note_folder)
    db.commit()
    db.refresh(db_note_folder)
    return db_note_folder

@router.get("/folders", response_model=List[schemas.NoteFolder])
def read_note_folders(skip: int = 0, limit: int = 100, db: Session = Depends(get_db)):
    note_folders = db.query(models.NoteFolder).offset(skip).limit(limit).all()
    return note_folders
