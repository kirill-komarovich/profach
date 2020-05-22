import * as functions from 'firebase-functions';
import * as admin from 'firebase-admin';

admin.initializeApp();

const USERS_COLLECTION = 'users';
const firestore = admin.firestore()

export const userCreated = functions.auth.user().onCreate((user) => {
  // TODO: find if exist
  return firestore.collection(USERS_COLLECTION).doc(user.uid).set({
    email: user.email,
    displayName: user.displayName,
    active: true,
  })
})

export const userDeleted = functions.auth.user().onCreate((user) => {
  return firestore.collection(USERS_COLLECTION).doc(user.uid).set({ active: false }, { merge: true })
})
