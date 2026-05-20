from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session
from typing import List
from app import schemas, models
from app.database import SessionLocal

router = APIRouter(
    prefix="/nebulas",
    tags=["nebulas"],
)

def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()

@router.post("/", response_model=schemas.Nebula)
def create_nebula(nebula: schemas.NebulaCreate, db: Session = Depends(get_db)):
    db_nebula = models.Nebula(**nebula.dict())
    db.add(db_nebula)
    db.commit()
    db.refresh(db_nebula)
    return db_nebula

@router.get("/", response_model=List[schemas.Nebula])
def read_nebulas(skip: int = 0, limit: int = 100, db: Session = Depends(get_db)):
    nebulas = db.query(models.Nebula).offset(skip).limit(limit).all()
    return nebulas

@router.get("/{nebula_id}", response_model=schemas.Nebula)
def read_nebula(nebula_id: str, db: Session = Depends(get_db)):
    db_nebula = db.query(models.Nebula).filter(models.Nebula.id == nebula_id).first()
    if db_nebula is None:
        raise HTTPException(status_code=404, detail="Nebula not found")
    return db_nebula

@router.put("/{nebula_id}", response_model=schemas.Nebula)
def update_nebula(nebula_id: str, nebula: schemas.NebulaUpdate, db: Session = Depends(get_db)):
    db_nebula = db.query(models.Nebula).filter(models.Nebula.id == nebula_id).first()
    if db_nebula is None:
        raise HTTPException(status_code=404, detail="Nebula not found")
    for var, value in vars(nebula).items():
        setattr(db_nebula, var, value) if value else None
    db.commit()
    db.refresh(db_nebula)
    return db_nebula

@router.delete("/{nebula_id}", response_model=schemas.Nebula)
def delete_nebula(nebula_id: str, db: Session = Depends(get_db)):
    db_nebula = db.query(models.Nebula).filter(models.Nebula.id == nebula_id).first()
    if db_nebula is None:
        raise HTTPException(status_code=404, detail="Nebula not found")
    db.delete(db_nebula)
    db.commit()
    return db_nebula
