export interface Notification {
  content: Content[]
  page: number
  size: number
  totalElements: number
  first: boolean
  numberOfElements: number
  last: boolean
  hasContent: boolean
  totalPages: number
  pageable: Pageable
  empty: boolean
  number: number
}

export interface Content {
  id: number
  expirationDate: string
  message: Message
  notificationDate: string
  keycloakUserId: string
  read: boolean
}

export interface Message {
  simulationStatus: string
  simulationId: number
  parentSupplierName: string
  classification: Classification
  completedBy: CompletedBy
  reason?: string
}

export interface Classification {
  id: number
  name: string
}

export interface CompletedBy {
  code: number
  keycloakUserId: string
  name: string
  areaCode?: number
  positionCode?: number
  email?: string
}

export interface Pageable {
  sort: Sort
  pageSize: number
  pageNumber: number
  offset: number
  paged: boolean
  unpaged: boolean
}

export interface Sort {
  unsorted: boolean
  sorted: boolean
  empty: boolean
}
