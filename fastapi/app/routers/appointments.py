from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session
from typing import List
from app import schemas, models
from app.database import SessionLocal

router = APIRouter(
    prefix="/appointments",
    tags=["appointments"],
)

def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()

@router.post("/", response_model=schemas.Appointment)
def create_appointment(appointment: schemas.AppointmentCreate, db: Session = Depends(get_db)):
    db_appointment = models.Appointment(**appointment.dict())
    db.add(db_appointment)
    db.commit()
    db.refresh(db_appointment)
    return db_appointment

@router.get("/", response_model=List[schemas.Appointment])
def read_appointments(skip: int = 0, limit: int = 100, db: Session = Depends(get_db)):
    appointments = db.query(models.Appointment).offset(skip).limit(limit).all()
    return appointments

@router.get("/{appointment_id}", response_model=schemas.Appointment)
def read_appointment(appointment_id: str, db: Session = Depends(get_db)):
    db_appointment = db.query(models.Appointment).filter(models.Appointment.id == appointment_id).first()
    if db_appointment is None:
        raise HTTPException(status_code=404, detail="Appointment not found")
    return db_appointment

@router.put("/{appointment_id}", response_model=schemas.Appointment)
def update_appointment(appointment_id: str, appointment: schemas.AppointmentUpdate, db: Session = Depends(get_db)):
    db_appointment = db.query(models.Appointment).filter(models.Appointment.id == appointment_id).first()
    if db_appointment is None:
        raise HTTPException(status_code=404, detail="Appointment not found")
    for var, value in vars(appointment).items():
        setattr(db_appointment, var, value) if value else None
    db.commit()
    db.refresh(db_appointment)
    return db_appointment

@router.delete("/{appointment_id}", response_model=schemas.Appointment)
def delete_appointment(appointment_id: str, db: Session = Depends(get_db)):
    db_appointment = db.query(models.Appointment).filter(models.Appointment.id == appointment_id).first()
    if db_appointment is None:
        raise HTTPException(status_code=404, detail="Appointment not found")
    db.delete(db_appointment)
    db.commit()
    return db_appointment
