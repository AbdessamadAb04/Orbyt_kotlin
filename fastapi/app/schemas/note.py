from pydantic import BaseModel
from datetime import datetime
from typing import Optional

class NoteBase(BaseModel):
    folder_id: Optional[str] = None
    title: Optional[str] = None
    content: str
    color_hex: str
    is_pinned: bool

class NoteCreate(NoteBase):
    pass

class NoteUpdate(NoteBase):
    pass

class Note(NoteBase):
    id: str
    created_at: datetime
    updated_at: datetime

    class Config:
        orm_mode = True

class NoteFolderBase(BaseModel):
    name: str
    color_hex: str

class NoteFolderCreate(NoteFolderBase):
    pass

class NoteFolderUpdate(NoteFolderBase):
    pass

class NoteFolder(NoteFolderBase):
    id: str
    created_at: datetime
    updated_at: datetime

    class Config:
        orm_mode = True
