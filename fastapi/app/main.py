from fastapi import FastAPI
from app.routers import appointments, habits, nebulas, notes, tasks

app = FastAPI()

app.include_router(appointments.router)
app.include_router(habits.router)
app.include_router(nebulas.router)
app.include_router(notes.router)
app.include_router(tasks.router)

@app.get("/")
def read_root():
    return {"message": "Welcome to the Orbyt FastAPI backend!"}
