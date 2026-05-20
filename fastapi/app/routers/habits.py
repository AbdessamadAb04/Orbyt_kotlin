from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session
from typing import List
from app import schemas, models
from app.database import SessionLocal

router = APIRouter(
    prefix="/habits",
    tags=["habits"],
)

def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()

@router.post("/", response_model=schemas.Habit)
def create_habit(habit: schemas.HabitCreate, db: Session = Depends(get_db)):
    db_habit = models.Habit(**habit.dict())
    db.add(db_habit)
    db.commit()
    db.refresh(db_habit)
    return db_habit

@router.get("/", response_model=List[schemas.Habit])
def read_habits(skip: int = 0, limit: int = 100, db: Session = Depends(get_db)):
    habits = db.query(models.Habit).offset(skip).limit(limit).all()
    return habits

@router.post("/logs", response_model=schemas.HabitLog)
def create_habit_log(habit_log: schemas.HabitLogCreate, db: Session = Depends(get_db)):
    db_habit_log = models.HabitLog(**habit_log.dict())
    db.add(db_habit_log)
    db.commit()
    db.refresh(db_habit_log)
    return db_habit_log

@router.get("/logs/{habit_id}", response_model=List[schemas.HabitLog])
def read_habit_logs(habit_id: str, skip: int = 0, limit: int = 100, db: Session = Depends(get_db)):
    habit_logs = db.query(models.HabitLog).filter(models.HabitLog.habit_id == habit_id).offset(skip).limit(limit).all()
    return habit_logs
